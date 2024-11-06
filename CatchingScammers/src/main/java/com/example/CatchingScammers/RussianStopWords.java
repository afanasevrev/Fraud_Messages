package com.example.CatchingScammers;

import smile.nlp.dictionary.StopWords;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public enum RussianStopWords implements StopWords {
    DEFAULT("src/main/resources/static/stop_words_ru.txt");
    private HashSet<String> dict = new HashSet();
    private RussianStopWords(String resource) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
            Throwable var5 = null;
            try {
                String line = null;
                while((line = input.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        this.dict.add(line);
                    }
                }
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (input != null) {
                    if (var5 != null) {
                        try {
                            input.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        input.close();
                    }
                }

            }
        } catch (IOException var17) {
            var17.printStackTrace();
        }
    }
    @Override
    public boolean contains(String word) {
        return this.dict.contains(word);
    }
    @Override
    public int size() {
        return dict.size();
    }
    @Override
    public Iterator<String> iterator() {
        return this.dict.iterator();
    }
}
