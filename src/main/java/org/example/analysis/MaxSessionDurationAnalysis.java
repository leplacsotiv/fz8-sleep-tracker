package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public class MaxSessionDurationAnalysis implements SleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        long maxMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .max()
                .orElse(0);

        return new SleepAnalysisResult<>("Maximum session duration (minutes)", maxMinutes);
    }
}
