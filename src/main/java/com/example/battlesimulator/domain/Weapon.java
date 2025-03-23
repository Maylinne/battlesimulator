package com.example.battlesimulator.domain;

import com.example.battlesimulator.service.DiceRoller;
import com.example.battlesimulator.service.Roll;

public class Weapon {

    private final Roll damageRoll;

    private int conversion;

    public Weapon(Roll roll, int conversion) {
        this.damageRoll = roll;
        this.conversion = conversion;
    }

    public int rollDamage() {
        return DiceRoller.roll(this.damageRoll);
    }

    public Roll getDamageRoll() {
        return damageRoll;
    }

    public int getConversion() {
        return conversion;
    }

}
