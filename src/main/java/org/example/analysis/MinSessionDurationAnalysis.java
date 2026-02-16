package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public class MinSessionDurationAnalysis implements SleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        long minMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .min()
                .orElse(0);

        return new SleepAnalysisResult<>("Minimum session duration (minutes)", minMinutes);
    }
}
