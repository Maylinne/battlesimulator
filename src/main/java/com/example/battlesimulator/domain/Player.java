package com.example.battlesimulator.domain;

import jakarta.persistence.*;

import java.util.Random;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int strength;
    private int agility;
    private int speed;
    private int stamina;
    private int health;
    private int willpower;
    private int perception;
    private boolean lockedForBattle;
    @Transient
    private int baseAttack;
    @Transient
    private int baseDefense;
    @Transient
    private int healthPoint;
    @Transient
    private int fatiguePoint;

    // region Getter, setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public int getBaseDefense() {
        return agility + speed + 75;
    }

    public int getHealthPoint() {
        return health;
    }

    public int getFatiguePoint() {
        return stamina + willpower;
    }

    public int getBaseAttack() {
        return strength + agility + speed;
    }

    public int getBaseInitiative() {
        return speed + perception;
    }

    // endregion

    public boolean isLockedForBattle() {
        return lockedForBattle;
    }

    public void setLockedForBattle(boolean lockedForBattle) {
        this.lockedForBattle = lockedForBattle;
    }

    public void lockForBattle() {
        this.lockedForBattle = true;
    }

}
