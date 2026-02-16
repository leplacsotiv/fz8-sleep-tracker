package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageSessionDurationAnalysisTest {

    @Test
    void analyze_returnsAverageDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 1, 23, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 22, 0), LocalDateTime.of(2025, 10, 3, 0, 0), SleepQuality.NORMAL)
        );

        AverageSessionDurationAnalysis analysis = new AverageSessionDurationAnalysis();
        SleepAnalysisResult<Double> result = analysis.analyze(sessions);

        assertEquals(90.0, result.value(), 0.000001);
    }

    @Test
    void analyze_emptyList_returnsZero() {
        AverageSessionDurationAnalysis analysis = new AverageSessionDurationAnalysis();
        SleepAnalysisResult<Double> result = analysis.analyze(List.of());
        assertEquals(0.0, result.value(), 0.000001);
    }
}
