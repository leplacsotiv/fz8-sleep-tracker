package org.example.analysis;

import org.example.domain.Chronotype;
import org.example.domain.SleepingSession;

import java.time.LocalTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChronotypeAnalysis implements SleepAnalysis<Chronotype> {

    private static final LocalTime OWL_SLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);

    private static final LocalTime LARK_SLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult<Chronotype> analyze(java.util.List<SleepingSession> sessions) {
        SleepAnalysis.requireSessions(sessions);

        Map<Chronotype, Long> counts = NightSleepExtractor.extract(sessions)
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Chronotype result = chooseChronotype(counts);

        return new SleepAnalysisResult<>("User chronotype (OWL/LARK/DOVE)", result);
    }

    private Chronotype classifyNight(NightSleepExtractor.NightSleep nightSleep) {
        LocalTime sleepTime = nightSleep.sleepStart().toLocalTime();
        LocalTime wakeTime = nightSleep.sleepEnd().toLocalTime();

        boolean isOwl = sleepTime.isAfter(OWL_SLEEP_AFTER) && wakeTime.isAfter(OWL_WAKE_AFTER);
        if (isOwl) return Chronotype.OWL;

        boolean isLark = sleepTime.isBefore(LARK_SLEEP_BEFORE) && wakeTime.isBefore(LARK_WAKE_BEFORE);
        if (isLark) return Chronotype.LARK;

        return Chronotype.DOVE;
    }

    private Chronotype chooseChronotype(Map<Chronotype, Long> counts) {
        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long doves = counts.getOrDefault(Chronotype.DOVE, 0L);

        long max = Math.max(owls, Math.max(larks, doves));
        long winners = Stream.of(owls, larks, doves).filter(c -> c == max).count();

        if (winners > 1) return Chronotype.DOVE;
        if (max == owls) return Chronotype.OWL;
        if (max == larks) return Chronotype.LARK;
        return Chronotype.DOVE;
    }
}
