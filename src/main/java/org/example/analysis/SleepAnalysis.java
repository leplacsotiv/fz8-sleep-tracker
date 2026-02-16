package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;
import java.util.Objects;

@FunctionalInterface
public interface SleepAnalysis<T> {
    SleepAnalysisResult<T> analyze(List<SleepingSession> sessions);

    static void requireSessions(List<SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "sessions is null");
    }
}
