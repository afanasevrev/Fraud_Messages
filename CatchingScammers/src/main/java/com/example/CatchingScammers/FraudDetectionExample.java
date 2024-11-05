package com.example.CatchingScammers;
import smile.nlp.tokenizer.SimpleTokenizer;
import smile.nlp.stemmer.PorterStemmer;
import smile.nlp.normalizer.SimpleNormalizer;
import smile.nlp.dictionary.EnglishStopWords;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FraudDetectionExample {
    public static void main(String[] args) {
        String text = "This is an example sentence showing the functionality of SMILE NLP package.";

        // Токенизация
        SimpleTokenizer tokenizer = new SimpleTokenizer();
        String[] tokens = tokenizer.split(text);
        System.out.println("Tokens: " + Arrays.toString(tokens));

        // Удаление стоп-слов
        List<String> filteredTokens = Arrays.stream(tokens)
                .filter(token -> !EnglishStopWords.DEFAULT.contains(token.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Filtered Tokens: " + filteredTokens);

        // Стемминг
        PorterStemmer stemmer = new PorterStemmer();
        List<String> stemmedTokens = filteredTokens.stream()
                .map(stemmer::stem)
                .collect(Collectors.toList());

        System.out.println("Stemmed Tokens: " + stemmedTokens);
    }
}
