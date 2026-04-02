package ru.offer.candidate.entity;

import lombok.Getter;

@Getter
public enum Level {
    JUNIOR("Junior"), MIDDLE("Middle"), SENIOR("Senior");

    private final String levelName;

    Level(String levelName) {
        this.levelName = levelName;
    }
}
