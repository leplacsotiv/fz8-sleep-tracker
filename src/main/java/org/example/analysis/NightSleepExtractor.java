package org.example.analysis;

import org.example.domain.SleepingSession;
import org.example.domain.TimeInterval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class NightSleepExtractor {

    private final List<SleepingSession> sessions;

    public NightSleepExtractor(List<SleepingSession> sessions) {
        this.sessions = sessions;
    }

    public record NightSleep(LocalDate nightDate, LocalDateTime sleepStart, LocalDateTime sleepEnd) {
    }

    public Stream<NightSleep> extract() {
        return NightWindows.nightDates(sessions)
                .flatMap(d -> nightSleepForDate(d).stream());
    }

    private Optional<NightSleep> nightSleepForDate(LocalDate nightDate) {
        LocalDateTime from = NightWindows.nightStart(nightDate);
        LocalDateTime to = NightWindows.nightEnd(nightDate);

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(s -> s.intersects(new TimeInterval(from, to)))
                .toList();

        if (nightSessions.isEmpty()) {
            return Optional.empty();
        }

        LocalDateTime sleepStart = nightSessions.stream()
                .map(SleepingSession::start)
                .min(Comparator.naturalOrder())
                .orElseThrow(() ->
                        new IllegalStateException("Cannot determine sleepStart for night " + nightDate)
                );


        LocalDateTime sleepEnd = nightSessions.stream()
                .map(SleepingSession::end)
                .max(Comparator.naturalOrder())
                .orElseThrow(() ->
                        new IllegalStateException("Cannot determine sleepStart for night " + nightDate)
                );


        return Optional.of(new NightSleep(nightDate, sleepStart, sleepEnd));
    }
}
