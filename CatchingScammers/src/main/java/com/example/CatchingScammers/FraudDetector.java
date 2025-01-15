package com.example.CatchingScammers;

import com.example.CatchingScammers.db.TextsEntity;
import com.example.CatchingScammers.db.TextsService;
import opennlp.tools.doccat.*;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс обучает модель и выдает предсказание на основе Байесовского алгоритма
 */
public class FraudDetector {
    @Autowired
    private TextsService textsService;
    private static final String resource = "/static/model.txt"; // Используйте / для начала пути
    public static DoccatModel model;
    public void detector() {
        try (InputStream inputStream = FraudDetector.class.getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + resource);
            }
            ObjectStream<String> lineStream = new PlainTextByLineStream(new InputStreamFactory() {
                @Override
                public InputStream createInputStream() throws IOException {
                    return inputStream;
                }
            }, StandardCharsets.UTF_8);
            // Добавляем в БД наш текст для обучения
            String line;
            while ((line = lineStream.read()) != null) {
                textsService.createText(new TextsEntity(line));
            }
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