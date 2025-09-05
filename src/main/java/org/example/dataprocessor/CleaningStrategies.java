package org.example.dataprocessor;

import org.example.dataprocessor.enums.CleaningType;
import java.util.*;
import java.util.stream.Collectors;

public class CleaningStrategies {

    public interface CleaningStrategy {
        List<Integer> clean(List<Integer> data);
    }

    public static Map<CleaningType, CleaningStrategy> register() {
        Map<CleaningType, CleaningStrategy> map = new HashMap<>();
        map.put(CleaningType.REMOVE_NEGATIVES,
                d -> d.stream().filter(x -> x >= 0).collect(Collectors.toList()));
        map.put(CleaningType.REPLACE_NEGATIVES_WITH_ZERO,
                d -> d.stream().map(x -> x < 0 ? 0 : x).collect(Collectors.toList()));
        return map;
    }
}
