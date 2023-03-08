package com.silah.projecthub.entities;

public enum Category {
    WEB, MOBILE, DESKTOP, EMBEDDED, OTHER;

    public boolean isValid() {
        return this == WEB || this == MOBILE || this == DESKTOP || this == EMBEDDED || this == OTHER;
    }
}
