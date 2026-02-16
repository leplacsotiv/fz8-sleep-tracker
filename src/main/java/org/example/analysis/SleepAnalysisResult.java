package org.example.analysis;

import java.util.Objects;

public record SleepAnalysisResult<T>(String description, T value) {

    public SleepAnalysisResult {
        Objects.requireNonNull(description, "description is null");
        Objects.requireNonNull(value, "value is null");
        if (description.isBlank()) {
            throw new IllegalArgumentException("description is blank");
        }
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}
