package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSessionDurationAnalysisTest {

    @Test
    void analyze_returnsMaximumDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 30), LocalDateTime.of(2025, 10, 3, 15, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 2, 6, 0), SleepQuality.GOOD)
        );

        MaxSessionDurationAnalysis analysis = new MaxSessionDurationAnalysis();
        SleepAnalysisResult<Long> result = analysis.apply(sessions);

        assertEquals(480L, result.value());
    }

    @Test
    void analyze_emptyList_returnsZero() {
        MaxSessionDurationAnalysis analysis = new MaxSessionDurationAnalysis();
        SleepAnalysisResult<Long> result = analysis.apply(List.of());
        assertEquals(0L, result.value());
    }
}
