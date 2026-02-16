package org.example.analysis;

import org.example.domain.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InsomniaNightsAnalysis implements SleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        long insomniaNights = NightWindows.nightDates(sessions)
                .filter(nightDate -> isInsomniaNight(nightDate, sessions))
                .count();

        return new SleepAnalysisResult<>("Number of insomnia nights (no sleep between 00:00 and 06:00)", insomniaNights);
    }

    private boolean isInsomniaNight(LocalDate nightDate, List<SleepingSession> sessions) {
        LocalDateTime from = NightWindows.nightStart(nightDate);
        LocalDateTime to = NightWindows.nightEnd(nightDate);

        boolean sleptThisNight = sessions.stream()
                .anyMatch(s -> s.intersects(from, to));

        return !sleptThisNight;
    }
}
