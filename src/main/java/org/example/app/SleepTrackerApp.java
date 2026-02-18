package org.example.app;

import org.example.analysis.*;
import org.example.domain.SleepingSession;
import org.example.io.SleepLogParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class SleepTrackerApp {

    private SleepTrackerApp() {
    }

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.err.println("Usage: java -jar sleep-analyzer.jar <path-to-sleep-log>");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        SleepLogParser parser = new SleepLogParser();
        List<SleepingSession> sessions = parser.parse(path);

        List<SleepAnalysis<?>> analyses = List.of(
                new TotalSessionsAnalysis(),
                new MinSessionDurationAnalysis(),
                new MaxSessionDurationAnalysis(),
                new AverageSessionDurationAnalysis(),
                new BadQualitySessionsCountAnalysis(),
                new InsomniaNightsAnalysis(),
                new ChronotypeAnalysis()
        );

        analyses.stream()
                .map(a -> a.apply(sessions))
                .forEach(System.out::println);
    }
}
