package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public final class MaxSessionDurationAnalysis extends BaseSleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> compute(List<SleepingSession> sessions) {

        long maxMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .max()
                .orElse(0);

        return new SleepAnalysisResult<>("Maximum session duration (minutes)", maxMinutes);
    }
}
