package com.example.CatchingScammers.db;

import java.util.List;

public interface TextsService {
    String createText(TextsEntity textsEntity);
    List<TextsEntity> readTexts();
    TextsEntity readText(Long id);
    String deleteText(Long id);
    String updateText(TextsEntity entity, Long id);
}
