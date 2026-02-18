package org.example.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public record TimeInterval(LocalDateTime from, LocalDateTime to) {

    public TimeInterval {
        Objects.requireNonNull(from, "from is null");
        Objects.requireNonNull(to, "to is null");
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("to must be after from. from=" + from + ", to=" + to);
        }
    }

    public boolean intersects(TimeInterval other) {
        Objects.requireNonNull(other, "other is null");
        return from.isBefore(other.to) && to.isAfter(other.from);
    }
}
