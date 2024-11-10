package com.example.CatchingScammers;

import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class FormController {
    DocumentCategorizerME categorizer = new DocumentCategorizerME(FraudDetector.model);
    @PostMapping("/submit")
    @ResponseBody
    public String submitForm(@RequestParam String userInput) {
        String[] message = userInput.split(" ");
        double[] outcomes = categorizer.categorize(message);
        String category = categorizer.getBestCategory(outcomes);
        String text = "Не мошенники";
        if (category.equals("fraud")) text = "Мошенники";
        try {
            return text;
        } catch(Exception e) {
            return "Ошибка при обработке данных";
        }
    }
}
