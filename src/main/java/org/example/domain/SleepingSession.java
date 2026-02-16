package org.example.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public record SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality quality) {

    public SleepingSession {
        Objects.requireNonNull(start, "start is null");
        Objects.requireNonNull(end, "end is null");
        Objects.requireNonNull(quality, "quality is null");

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Session end must be after start. start=" + start + ", end=" + end);
        }
    }

    public long durationMinutes() {
        return Duration.between(start, end).toMinutes();
    }

    public boolean intersects(LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(from, "from is null");
        Objects.requireNonNull(to, "to is null");
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("to must be after from. from=" + from + ", to=" + to);
        }
        return start.isBefore(to) && end.isAfter(from);
    }
}
