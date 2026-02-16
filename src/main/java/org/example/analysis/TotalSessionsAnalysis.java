package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

public class TotalSessionsAnalysis implements SleepAnalysis<Long> {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);
        return new SleepAnalysisResult<>("Total number of sleeping sessions", (long) sessions.size());
    }
}
