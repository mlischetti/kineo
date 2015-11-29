package com.kinesio.util.logback;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
/**
 *
 * Created by mlischetti on 11/27/15

 * Formats exceptions without new lines in stack traces (grep friendly)
 */
public class OneLineExceptionConverter extends ThrowableHandlingConverter {

    public String convert(ILoggingEvent event) {
        StringBuilder buf = new StringBuilder(32);
        IThrowableProxy tp = event.getThrowableProxy();
        if (tp == null)
            return CoreConstants.EMPTY_STRING;

        while (tp != null) {
            printThrowableProxy(buf, tp);
            tp = tp.getCause();
        }
        return buf.toString();
    }

    void printThrowableProxy(StringBuilder buf, IThrowableProxy tp) {
        ThrowableProxyUtil.subjoinFirstLine(buf, tp);
        buf.append("\\n");
        StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();
        int commonFrames = tp.getCommonFrames();
        int maxIndex = stepArray.length;
        if (commonFrames > 0)
            maxIndex -= commonFrames;

        for (int i = 0; i < maxIndex; i++) {
            String string = stepArray[i].toString();
            buf.append(CoreConstants.TAB);
            buf.append(string);
            ThrowableProxyUtil.subjoinPackagingData(buf, stepArray[i]);
            buf.append("\\n");
        }
        if (commonFrames > 0) {
            buf.append("\t... " + tp.getCommonFrames()).append(" common frames omitted").append("\\n");
        }
    }
}