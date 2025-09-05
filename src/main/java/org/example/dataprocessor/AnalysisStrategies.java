package org.example.dataprocessor;

import org.example.dataprocessor.enums.AnalysisType;
import java.util.*;

public class AnalysisStrategies {

    public interface AnalysisStrategy {
        double analyze(List<Integer> data);
    }

    public static Map<AnalysisType, AnalysisStrategy> register() {
        Map<AnalysisType, AnalysisStrategy> map = new HashMap<>();

        map.put(AnalysisType.MEAN, d -> d.isEmpty() ? Double.NaN :
                d.stream().mapToDouble(x -> x).average().orElse(Double.NaN));

        map.put(AnalysisType.MEDIAN, d -> {
            if (d.isEmpty()) return Double.NaN;
            List<Integer> sorted = new ArrayList<>(d);
            Collections.sort(sorted);
            int n = sorted.size();
            return n % 2 == 1 ? sorted.get(n / 2)
                    : (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
        });

        map.put(AnalysisType.STD_DEV, d -> {
            if (d.isEmpty()) return Double.NaN;
            double mean = d.stream().mapToDouble(x -> x).average().orElse(0);
            double var = d.stream().mapToDouble(x -> Math.pow(x - mean, 2)).average().orElse(0);
            return Math.sqrt(var);
        });

        map.put(AnalysisType.P90_NEAREST_RANK, d -> {
            if (d.isEmpty()) return Double.NaN;
            List<Integer> sorted = new ArrayList<>(d);
            Collections.sort(sorted);
            int rank = (int) Math.ceil(0.9 * sorted.size());
            return sorted.get(rank - 1);
        });

        map.put(AnalysisType.TOP3_FREQUENT_COUNT_SUM, d -> {
            if (d.isEmpty()) return 0.0;
            Map<Integer,Integer> freq = new HashMap<>();
            for (int x : d) freq.put(x, freq.getOrDefault(x, 0) + 1);
            return freq.entrySet().stream()
                    .sorted((a,b)->{
                        int cmp = Integer.compare(b.getValue(), a.getValue());
                        return cmp != 0 ? cmp : Integer.compare(a.getKey(), b.getKey());
                    })
                    .limit(3).mapToInt(Map.Entry::getValue).sum();
        });

        return map;
    }
}
