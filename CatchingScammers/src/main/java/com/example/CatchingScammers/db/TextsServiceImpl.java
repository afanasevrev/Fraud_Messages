package com.example.CatchingScammers.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TextsServiceImpl implements TextsService {
    @Autowired
    private TextsRepository textsRepository;
    @Override
    public String createText(TextsEntity entity) {
        textsRepository.save(entity);
        return "Запись успешно добавлена в БД";
    }
    @Override
    public List<TextsEntity> readTexts() {
        return textsRepository.findAll();
    }
    @Override
    public TextsEntity readText(Long id) {
        return textsRepository.findById(id).get();
    }
    @Override
    public String deleteText(Long id) {
        textsRepository.deleteById(id);
        return "Текст удален из БД";
    }
    @Override
    public String updateText(TextsEntity entity, Long id) {
        TextsEntity textsEntity = textsRepository.findById(id).get();
        textsEntity.setText(entity.getText());
        textsRepository.save(textsEntity);
        return "Текст обновлен";
    }
}
