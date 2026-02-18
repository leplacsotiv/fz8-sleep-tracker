package org.example.analysis;

import org.example.domain.SleepingSession;
import org.example.domain.TimeInterval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class InsomniaNightsAnalysis extends BaseSleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> compute(List<SleepingSession> sessions) {

        long insomniaNights = NightWindows.nightDates(sessions)
                .filter(nightDate -> isInsomniaNight(nightDate, sessions))
                .count();

        return new SleepAnalysisResult<>("Number of insomnia nights (no sleep between 00:00 and 06:00)", insomniaNights);
    }

    private boolean isInsomniaNight(LocalDate nightDate, List<SleepingSession> sessions) {
        LocalDateTime from = NightWindows.nightStart(nightDate);
        LocalDateTime to = NightWindows.nightEnd(nightDate);

        boolean sleptThisNight = sessions.stream()
                .anyMatch(s -> s.intersects(new TimeInterval(from, to)));

        return !sleptThisNight;
    }
}
