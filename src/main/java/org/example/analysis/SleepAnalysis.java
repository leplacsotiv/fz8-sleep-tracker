package org.example.analysis;

import org.example.domain.SleepingSession;

import java.util.List;

@FunctionalInterface
public interface SleepAnalysis<T> {
    SleepAnalysisResult<T> apply(List<SleepingSession> sessions);
}
