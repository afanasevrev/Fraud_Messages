package com.example.CatchingScammers;

import opennlp.tools.doccat.*;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FraudDetector {
    private static final String resource = "src/main/resources/static/model.txt";
    public static void main(String[] args) {
        try {
            InputStreamFactory dataIn = new InputStreamFactory() {
                @Override
                public FileInputStream createInputStream() throws IOException {
                    return new FileInputStream(resource);
                }
            };
            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            DoccatModel model;
            try {
                TrainingParameters params = new TrainingParameters();
                params.put(TrainingParameters.ITERATIONS_PARAM, 100);
                params.put(TrainingParameters.CUTOFF_PARAM, 1);
                NaiveBayesTrainer trainer = new NaiveBayesTrainer();
                DoccatFactory factory = new DoccatFactory();
                model = DocumentCategorizerME.train("ru", sampleStream, params, factory);
            } finally {
                sampleStream.close();
            }

            DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

            String message = "Завтра в 8:00 собираемся в холле университета";
            String[] message1 = message.split(" ");
            double[] outcomes = categorizer.categorize(message1);
            System.out.println(outcomes.length);
            String category = categorizer.getBestCategory(outcomes);
            System.out.println("Категория сообщения: " + category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
