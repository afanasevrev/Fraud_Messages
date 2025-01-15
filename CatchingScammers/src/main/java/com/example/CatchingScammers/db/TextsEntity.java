package com.example.CatchingScammers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "texts")
public class TextsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;
    public TextsEntity() {
    }
    public TextsEntity(String text) {
        this.text = text;
    }
}
