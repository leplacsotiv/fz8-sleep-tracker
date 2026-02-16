package org.example.analysis;

import org.example.domain.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class NightSleepExtractor {

    private NightSleepExtractor() {
    }

    public record NightSleep(LocalDate nightDate, LocalDateTime sleepStart, LocalDateTime sleepEnd) {
    }

    public static Stream<NightSleep> extract(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);
        return NightWindows.nightDates(sessions)
                .flatMap(d -> nightSleepForDate(d, sessions).stream());
    }

    private static Optional<NightSleep> nightSleepForDate(LocalDate nightDate, List<SleepingSession> sessions) {
        LocalDateTime from = NightWindows.nightStart(nightDate);
        LocalDateTime to = NightWindows.nightEnd(nightDate);

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(s -> s.intersects(from, to))
                .toList();

        if (nightSessions.isEmpty()) {
            return Optional.empty();
        }

        LocalDateTime sleepStart = nightSessions.stream()
                .map(SleepingSession::start)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        LocalDateTime sleepEnd = nightSessions.stream()
                .map(SleepingSession::end)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        return Optional.of(new NightSleep(nightDate, sleepStart, sleepEnd));
    }
}
