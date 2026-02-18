package org.example.domain;

import java.util.Locale;

public enum SleepQuality {
    GOOD,
    NORMAL,
    BAD;

    public static SleepQuality from(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Sleep quality is null");
        }
        String normalized = raw.trim().toUpperCase(Locale.ROOT);
        return SleepQuality.valueOf(normalized);
    }
}
