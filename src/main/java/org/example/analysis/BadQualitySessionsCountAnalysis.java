package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;

import java.util.List;

public final class BadQualitySessionsCountAnalysis extends BaseSleepAnalysis<Long> {

    private final String description;

    public BadQualitySessionsCountAnalysis() {
        this("Number of BAD quality sessions");
    }

    public BadQualitySessionsCountAnalysis(String description) {
        this.description = description;
    }

    @Override
    protected SleepAnalysisResult<Long> compute(List<SleepingSession> sessions) {
        long badCount = sessions.stream()
                .filter(s -> s.quality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult<>(description, badCount);
    }
}
