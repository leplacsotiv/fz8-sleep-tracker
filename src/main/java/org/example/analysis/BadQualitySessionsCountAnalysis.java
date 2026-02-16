package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;

import java.util.List;

public class BadQualitySessionsCountAnalysis implements SleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        long badCount = sessions.stream()
                .filter(s -> s.quality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult<>("Number of BAD quality sessions", badCount);
    }
}
