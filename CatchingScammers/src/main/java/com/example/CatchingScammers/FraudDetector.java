package com.example.CatchingScammers;

import opennlp.tools.doccat.*;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import java.io.*;
import java.nio.charset.StandardCharsets;
/**
 * Класс обучает модель и выдает предсказание на основе Байесовского алгоритма
 */
public class FraudDetector {
    private static final String resource = "/static/model.txt"; // Используйте / для начала пути
    public static DoccatModel model;
    public void detector() {
        try (InputStream inputStream = FraudDetector.class.getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + resource);
            }
            // Используйте InputStream вместо FileInputStream напрямую
            ObjectStream<String> lineStream = new PlainTextByLineStream(new InputStreamFactory() {
                @Override
                public InputStream createInputStream() throws IOException {
                    return inputStream;
                }
            }, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            try {
                TrainingParameters params = new TrainingParameters();
                params.put(TrainingParameters.ITERATIONS_PARAM, 100);
                params.put(TrainingParameters.CUTOFF_PARAM, 1);
                DoccatFactory factory = new DoccatFactory();
                model = DocumentCategorizerME.train("ru", sampleStream, params, factory);
                System.out.println("The training of the model was successful");
            } finally {
                sampleStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}