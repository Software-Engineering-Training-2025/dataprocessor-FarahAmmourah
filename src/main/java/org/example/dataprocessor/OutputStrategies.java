package org.example.dataprocessor;

import org.example.dataprocessor.enums.OutputType;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class OutputStrategies {

    public interface OutputStrategy {
        void output(double result) throws Exception;
    }

    public static Map<OutputType, OutputStrategy> register() {
        Map<OutputType, OutputStrategy> map = new HashMap<>();

        map.put(OutputType.CONSOLE, r -> System.out.println("Result = " + r));

        map.put(OutputType.TEXT_FILE, r -> {
            File f = new File("target/result.txt");
            f.getParentFile().mkdirs();
            try (FileWriter fw = new FileWriter(f)) {
                fw.write("Result = " + r);
            }
        });

        return map;
    }
}
