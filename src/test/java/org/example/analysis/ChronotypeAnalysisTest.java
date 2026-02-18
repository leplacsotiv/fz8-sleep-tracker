package org.example.analysis;

import org.example.domain.Chronotype;
import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeAnalysisTest {

    private final ChronotypeAnalysis analysis = new ChronotypeAnalysis();

    @Test
    void returnsOwl_whenOwlNightsAreMajority() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 10, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 45), LocalDateTime.of(2025, 10, 3, 9, 30), SleepQuality.NORMAL)
        );

        assertEquals(Chronotype.OWL, analysis.apply(sessions).value());
    }

    @Test
    void returnsLark_whenLarkNightsAreMajority() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 30), LocalDateTime.of(2025, 10, 2, 6, 30), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 50), LocalDateTime.of(2025, 10, 3, 6, 40), SleepQuality.NORMAL)
        );

        assertEquals(Chronotype.LARK, analysis.apply(sessions).value());
    }

    @Test
    void returnsDove_whenTieBetweenTypes() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 10, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 30), LocalDateTime.of(2025, 10, 3, 6, 30), SleepQuality.NORMAL)
        );

        assertEquals(Chronotype.DOVE, analysis.apply(sessions).value());
    }

    @Test
    void ignoresDaySessions_whenCalculatingChronotype() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 30), LocalDateTime.of(2025, 10, 2, 6, 30), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 14, 0), LocalDateTime.of(2025, 10, 2, 15, 0), SleepQuality.NORMAL)
        );

        assertEquals(Chronotype.LARK, analysis.apply(sessions).value());
    }

    @Test
    void returnsDove_whenNoNightSleepExists_onlyDaySessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 30), LocalDateTime.of(2025, 10, 3, 15, 20), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 4, 13, 0), LocalDateTime.of(2025, 10, 4, 13, 40), SleepQuality.GOOD)
        );

        assertEquals(Chronotype.DOVE, analysis.apply(sessions).value());
    }
}
