package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public final class AverageSessionDurationAnalysis extends BaseSleepAnalysis<Double> {

    @Override
    public SleepAnalysisResult<Double> compute(List<SleepingSession> sessions) {

        double avgMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult<>("Average session duration (minutes)", avgMinutes);
    }
}
