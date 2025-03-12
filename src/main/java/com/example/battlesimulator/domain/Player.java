package com.example.battlesimulator.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    private int strength;
//    private int agility;
//    private int speed;
//    private int stamina;
    private int health;
//    private int willpower;
//    private int perception;
    private int baseAttack;
//    private int baseDefense;
//    private int healthPoint;
//    private int fatiguePoint;
}
