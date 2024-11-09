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
import java.util.Collections;

public class FraudDetector {
    private static final String resource = "src/main/resources/static/model.txt";
    public static void main(String[] args) {
        try {
            // Укажите путь к вашему тренировочному файлу с данными на русском языке
            InputStreamFactory dataIn = new InputStreamFactory() {
                @Override
                public FileInputStream createInputStream() throws IOException {
                    return new FileInputStream(resource);
                }
            };
            // Чтение и подготовка данных
            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            // Обучение модели
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
            // Использование модели
            DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
            // Пример сообщения для классификации
            String message = "Приглашаем вас посетить открытие нового магазина в вашем городе в эту субботу. Будет много акций и скидок!";
            double[] outcomes = categorizer.categorize(new String[]{message});
            String category = categorizer.getBestCategory(outcomes);
            System.out.println("Категория сообщения: " + category);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
