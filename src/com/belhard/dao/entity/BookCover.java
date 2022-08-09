package com.belhard.dao.entity;

public enum BookCover {
    HARD ("hard"),
    SOFT ("soft"),
    SPECIAL ("special");

    private final String cover;

    BookCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }
}
