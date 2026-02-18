package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;
import java.util.Objects;

public abstract class BaseSleepAnalysis<T> implements SleepAnalysis<T> {

    @Override
    public final SleepAnalysisResult<T> apply(List<SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "sessions is null");
        return compute(sessions);
    }

    protected abstract SleepAnalysisResult<T> compute(List<SleepingSession> sessions);
}
