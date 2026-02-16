package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public class AverageSessionDurationAnalysis implements SleepAnalysis<Double> {

    @Override
    public SleepAnalysisResult<Double> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        double avgMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult<>("Average session duration (minutes)", avgMinutes);
    }
}
