package com.example.CatchingScammers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class FormController {

    @PostMapping("/submit")
    @ResponseBody
    public String submitForm(@RequestParam String userInput) {
        try {
            return "Данные успешно отработаны";
        } catch(Exception e) {
            return "Ошибка при обработке данных";
        }
    }
}
