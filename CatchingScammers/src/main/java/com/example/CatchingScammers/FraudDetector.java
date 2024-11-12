package com.example.CatchingScammers;

import opennlp.tools.doccat.*;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
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
    //private static final String resource = "src/main/resources/static/model.txt";
    private static final String resource = "model.txt";
    public static DoccatModel model;
    public void detector() {
        InputStream inputStream = FraudDetector.class.getResourceAsStream(resource);
        try {
            InputStreamFactory dataIn = new InputStreamFactory() {
                @Override
                public FileInputStream createInputStream() throws IOException {
                    return new FileInputStream(resource);
                }
            };
            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            //DoccatModel model;

            try {
                TrainingParameters params = new TrainingParameters();
                params.put(TrainingParameters.ITERATIONS_PARAM, 100);
                params.put(TrainingParameters.CUTOFF_PARAM, 1);
                NaiveBayesTrainer trainer = new NaiveBayesTrainer();
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
