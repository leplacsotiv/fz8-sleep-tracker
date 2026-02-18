package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public final class TotalSessionsAnalysis extends BaseSleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> compute(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>("Total number of sleeping sessions", (long) sessions.size());
    }
}
