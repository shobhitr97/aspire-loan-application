package org.aspire.model;

public enum EntityAction {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");

    private final String name;

    EntityAction(String name) {
        this.name = name;
    }
}
