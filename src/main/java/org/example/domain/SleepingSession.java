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

    public boolean intersects(TimeInterval interval) {
        Objects.requireNonNull(interval, "interval is null");
        TimeInterval sessionInterval = new TimeInterval(start, end);
        return sessionInterval.intersects(interval);
    }
}
