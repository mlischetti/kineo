package com.kineo.web.exception;

/**
 * Created by mlischetti on 12/7/15.
 */
public class EntityNotFoundException extends RuntimeException {

    private final String entityType;
    private String searchMessage;

    public EntityNotFoundException(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }
}
