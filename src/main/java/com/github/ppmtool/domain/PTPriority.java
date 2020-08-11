package com.github.ppmtool.domain;

import java.util.stream.Stream;

public enum PTPriority {

    LOW(100), MEDIUM(200), HIGH(300);

    private int priority;

    private PTPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static PTPriority of(int priority) {
        return Stream.of(PTPriority.values())
                .filter(p -> p.getPriority() == priority)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
