package org.example.io;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleepLogParserTest {

    @TempDir
    Path tempDir;

    @Test
    void parse_validFile_parsesAllSessions() throws Exception {
        Path file = tempDir.resolve("sleep.csv");
        Files.writeString(file,
                """
                01.10.25 22:15;02.10.25 08:00;GOOD
                02.10.25 23:00;03.10.25 08:00;NORMAL
                03.10.25 14:30;03.10.25 15:20;NORMAL
                """
        );

        SleepLogParser parser = new SleepLogParser();
        List<SleepingSession> sessions = parser.parse(file);

        assertEquals(3, sessions.size());

        SleepingSession first = sessions.get(0);
        assertEquals(LocalDateTime.of(2025, 10, 1, 22, 15), first.start());
        assertEquals(LocalDateTime.of(2025, 10, 2, 8, 0), first.end());
        assertEquals(SleepQuality.GOOD, first.quality());
        assertEquals(585, first.durationMinutes());
    }

    @Test
    void parse_qualityWithTrailingSpaces_isTrimmed() throws Exception {
        Path file = tempDir.resolve("sleep.csv");
        Files.writeString(file, "03.10.25 23:30;04.10.25 06:20;BAD \n");

        SleepLogParser parser = new SleepLogParser();
        List<SleepingSession> sessions = parser.parse(file);

        assertEquals(1, sessions.size());
        assertEquals(SleepQuality.BAD, sessions.get(0).quality());
    }

    @Test
    void parse_invalidFormat_throwsWithLineNumber() throws Exception {
        Path file = tempDir.resolve("sleep.csv");
        Files.writeString(file, "01.10.25 22:15;02.10.25 08:00\n");

        SleepLogParser parser = new SleepLogParser();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
        assertTrue(ex.getMessage().contains("line 1"));
        assertTrue(ex.getMessage().contains("expected 3 parts"));
    }

    @Test
    void parse_invalidDate_throwsWithLineNumber() throws Exception {
        Path file = tempDir.resolve("sleep.csv");
        Files.writeString(file, "32.10.25 22:15;02.10.25 08:00;GOOD\n");

        SleepLogParser parser = new SleepLogParser();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
        assertTrue(ex.getMessage().contains("line 1"));
        assertTrue(ex.getMessage().contains("Invalid date/time"));
    }

    @Test
    void parse_endBeforeStart_throwsWithReason() throws Exception {
        Path file = tempDir.resolve("sleep.csv");
        Files.writeString(file, "02.10.25 08:00;01.10.25 22:15;GOOD\n");

        SleepLogParser parser = new SleepLogParser();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
        assertTrue(ex.getMessage().contains("line 1"));
        assertTrue(ex.getMessage().toLowerCase().contains("after start"));
    }
}
