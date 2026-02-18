package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSessionsAnalysisTest {

    @Test
    void analyze_returnsCorrectCount() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 2, 6, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 0), SleepQuality.NORMAL)
        );

        TotalSessionsAnalysis analysis = new TotalSessionsAnalysis();
        SleepAnalysisResult<Long> result = analysis.apply(sessions);

        assertEquals(2L, result.value());
        assertEquals("Total number of sleeping sessions", result.description());
    }

    @Test
    void analyze_emptyList_returnsZero() {
        TotalSessionsAnalysis analysis = new TotalSessionsAnalysis();
        SleepAnalysisResult<Long> result = analysis.apply(List.of());
        assertEquals(0L, result.value());
    }
}
