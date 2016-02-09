package com.kineo.web.filter;

import com.kineo.util.MediaType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class DumpFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DumpFilter.class);
    private String healthCheckURI;
    private static Set<String> acceptedMediaType = new HashSet<String>();

    static {
        acceptedMediaType.add(MediaType.APPLICATION_JSON);
    }

    private static class ByteArrayServletStream extends ServletOutputStream {

        ByteArrayOutputStream baos;

        ByteArrayServletStream(ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        public void write(int param) throws IOException {
            baos.write(param);
        }
    }

    private static class ByteArrayPrintWriter {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        private PrintWriter pw = new PrintWriter(baos);

        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter() {
            return pw;
        }

        public ServletOutputStream getStream() {
            return sos;
        }

        byte[] toByteArray() {
            return baos.toByteArray();
        }
    }

    private class BufferedServletInputStream extends ServletInputStream {

        ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        public int available() {
            return bais.available();
        }

        public int read() {
            return bais.read();
        }

        public int read(byte[] buf, int off, int len) {
            return bais.read(buf, off, len);
        }

    }

    private class BufferedRequestWrapper extends HttpServletRequestWrapper {

        ByteArrayInputStream bais;
        BufferedServletInputStream bsis;
        byte[] buffer;

        public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
            super(req);
            buffer = IOUtils.toByteArray(req.getInputStream());
        }

        public ServletInputStream getInputStream() {
            try {
                bais = new ByteArrayInputStream(buffer);
                bsis = new BufferedServletInputStream(bais);
            } catch (Exception e) {
                LOGGER.error("Could not get the input stream.", e);
            }

            return bsis;
        }

        public byte[] getBuffer() {
            return buffer;
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.healthCheckURI = filterConfig.getInitParameter("healthCheckURI");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (httpRequest.getRequestURI().equals(this.healthCheckURI)) {

            filterChain.doFilter(httpRequest, servletResponse);

        } else {

            HttpServletRequest bufferedRequest;

            try {

                if (acceptedMediaType.contains(httpRequest.getContentType())) {
                    bufferedRequest = new BufferedRequestWrapper(httpRequest);
                    String requestString = new String(((BufferedRequestWrapper) bufferedRequest).getBuffer());
                    LOGGER.info("REQUEST -> method: {}, uri: {}, queryString: {}, body: {}", httpRequest.getMethod(), httpRequest
                            .getRequestURI(), httpRequest.getQueryString(), requestString);
                } else {
                    bufferedRequest = httpRequest;
                    LOGGER.info("REQUEST -> method: {}, uri: {}, queryString: {}", httpRequest.getMethod(), httpRequest.getRequestURI(),
                            httpRequest.getQueryString());
                }

            } catch (Throwable e) {
                LOGGER.info("REQUEST -> method: {}, uri: {}, queryString: {}", httpRequest.getMethod(), httpRequest.getRequestURI(),
                        httpRequest.getQueryString());
                LOGGER.error("The request data could not be logged.", e);

                bufferedRequest = httpRequest;
            }

            final HttpServletResponse response = (HttpServletResponse) servletResponse;

            final ByteArrayPrintWriter pw = new ByteArrayPrintWriter();
            HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {
                public PrintWriter getWriter() {
                    return pw.getWriter();
                }

                public ServletOutputStream getOutputStream() {
                    return pw.getStream();
                }
            };

            filterChain.doFilter(bufferedRequest, wrappedResp);

            byte[] bytes = pw.toByteArray();
            response.getOutputStream().write(bytes);

            LOGGER.info("RESPONSE -> {}", new String(bytes));
        }
    }

    public void destroy() {
    }

}