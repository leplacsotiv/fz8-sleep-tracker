package org.example.analysis;

import org.example.domain.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public final class NightWindows {

    private static final LocalTime NOON = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private NightWindows() {
    }

    public static Stream<LocalDate> nightDates(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return Stream.empty();
        }

        SleepingSession first = sessions.getFirst();
        SleepingSession last = sessions.getLast();

        LocalDate startNightDate = first.start().toLocalTime().isAfter(NOON)
                ? first.start().toLocalDate().plusDays(1)
                : first.start().toLocalDate().minusDays(1);

        LocalDate endExclusive = last.end().toLocalDate().plusDays(1);
        return startNightDate.datesUntil(endExclusive);
    }

    public static LocalDateTime nightStart(LocalDate nightDate) {
        return nightDate.atStartOfDay();
    }

    public static LocalDateTime nightEnd(LocalDate nightDate) {
        return nightDate.atTime(NIGHT_END);
    }
}
