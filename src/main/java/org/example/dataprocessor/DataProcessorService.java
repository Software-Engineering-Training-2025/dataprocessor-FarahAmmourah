package org.example.dataprocessor;

import org.example.dataprocessor.enums.*;
import java.util.List;
import java.util.Map;

public class DataProcessorService {

    private final Map<CleaningType, CleaningStrategies.CleaningStrategy> cleaningStrategies;
    private final Map<AnalysisType, AnalysisStrategies.AnalysisStrategy> analysisStrategies;
    private final Map<OutputType, OutputStrategies.OutputStrategy> outputStrategies;

    public DataProcessorService() {
        cleaningStrategies = CleaningStrategies.register();
        analysisStrategies = AnalysisStrategies.register();
        outputStrategies = OutputStrategies.register();
    }

    public double process(CleaningType cleaningType,
                          AnalysisType analysisType,
                          OutputType outputType,
                          List<Integer> data) throws Exception {

        List<Integer> cleaned = cleaningStrategies.get(cleaningType).clean(data);
        double result = analysisStrategies.get(analysisType).analyze(cleaned);
        outputStrategies.get(outputType).output(result);
        return result;
    }
}
