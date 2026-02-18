package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public final class MinSessionDurationAnalysis extends BaseSleepAnalysis<Long> {

    @Override
    protected SleepAnalysisResult<Long> compute(List<SleepingSession> sessions) {
        long minMinutes = sessions.stream()
                .mapToLong(SleepingSession::durationMinutes)
                .min()
                .orElse(0);

        return new SleepAnalysisResult<>("Minimum session duration (minutes)", minMinutes);
    }
}
