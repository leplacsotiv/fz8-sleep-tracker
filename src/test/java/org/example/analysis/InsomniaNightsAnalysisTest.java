package org.example.analysis;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsomniaNightsAnalysisTest {

    private final InsomniaNightsAnalysis analysis = new InsomniaNightsAnalysis();

    @Test
    void noInsomnia_whenSleepFrom23To03_crossesNightWindow() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 3, 0), SleepQuality.NORMAL)
        );

        assertEquals(0L, analysis.analyze(sessions).value());
    }

    @Test
    void insomnia_whenOnlyDaySleepAfter07_doesNotCrossNightWindow() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 7, 0), LocalDateTime.of(2025, 10, 3, 11, 0), SleepQuality.GOOD)
        );

        assertEquals(2L, analysis.analyze(sessions).value());
    }

    @Test
    void startAfterNoon_rule_firstSessionAfter12_startsFromNextNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 30), LocalDateTime.of(2025, 10, 3, 15, 20), SleepQuality.NORMAL)
        );

        assertEquals(0L, analysis.analyze(sessions).value());
    }

    @Test
    void worksAcrossMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 31, 23, 0), LocalDateTime.of(2025, 11, 1, 5, 0), SleepQuality.GOOD)
        );

        assertEquals(0L, analysis.analyze(sessions).value());
    }

    @Test
    void insomnia_whenThereIsGapNightWithoutSessionsInWindow() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 2, 0), LocalDateTime.of(2025, 10, 1, 7, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 10, 0), LocalDateTime.of(2025, 10, 2, 11, 0), SleepQuality.NORMAL)
        );

        assertEquals(2L, analysis.analyze(sessions).value());
    }
}
