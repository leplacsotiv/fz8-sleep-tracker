package org.example.io;

import org.example.domain.SleepQuality;
import org.example.domain.SleepingSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class SleepLogParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> parse(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            List<String> cleaned = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            return IntStream.range(0, cleaned.size())
                    .mapToObj(i -> new IndexedLine(i + 1, cleaned.get(i)))
                    .map(this::parseLine)
                    .toList();

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read file: " + path, e);
        }
    }

    private SleepingSession parseLine(IndexedLine indexedLine) {
        String line = indexedLine.text();
        int lineNo = indexedLine.number();

        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format at line " + lineNo +
                    ": expected 3 parts separated by ';' but got " + parts.length +
                    ". Line: " + line);
        }

        try {
            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
            SleepQuality quality = SleepQuality.from(parts[2]);
            return new SleepingSession(start, end, quality);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time at line " + lineNo + ". Line: " + line, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid session at line " + lineNo +
                    ". Line: " + line + ". Reason: " + e.getMessage(), e);
        }
    }

    private record IndexedLine(int number, String text) {
        private IndexedLine {
            if (number < 1) throw new IllegalArgumentException("number must be >= 1");
            if (text == null) throw new IllegalArgumentException("text is null");
        }
    }
}
