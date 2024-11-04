package com.example.CatchingScammers;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import java.util.Arrays;

public class FraudDetectionExample {
    public static void main(String[] args) {
        // Создание конфигурации и контекста Spark
        SparkConf conf = new SparkConf().setAppName("FraudDetectionExample").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // Пример входных данных
        JavaRDD<String> textData = sc.parallelize(Arrays.asList(
                "This is a normal transaction",
                "WIN a FREE iPhone!!! Click here",
                "Important account information",
                "Congratulations, you've won $1,000,000!"
        ));

        // Векторизация текста с использованием CountVectorizer (в реальной задаче используйте TfidfVectorizer)
        Dataset<Row> textDataDF = spark.createDataFrame(textData.map(RowFactory::create),
                new StructType().add("text", DataTypes.StringType));

        CountVectorizerModel cvModel = new CountVectorizer()
                .setInputCol("text")
                .setOutputCol("features")
                .fit(textDataDF);

        Dataset<Row> featurizedData = cvModel.transform(textDataDF);

        // Пример меток (0 - нет фрода, 1 - фрод)
        Dataset<Row> labeledData = featurizedData.withColumn("label", functions.when(functions.col("text").contains("WIN"), 1).otherwise(0));

        // Разделение на обучающую и тестовую выборки
        Dataset<Row>[] splits = labeledData.randomSplit(new double[]{0.8, 0.2}, 1234L);
        Dataset<Row> train = splits[0];
        Dataset<Row> test = splits[1];

        // Обучение модели логистической регрессии
        LogisticRegression lr = new LogisticRegression();
        LogisticRegressionModel model = lr.fit(train);

        // Применение модели для предсказания
        Dataset<Row> predictions = model.transform(test);

        // Вывод результатов
        predictions.show();

        // Завершение контекста
        sc.close();
        spark.stop();
    }
}
