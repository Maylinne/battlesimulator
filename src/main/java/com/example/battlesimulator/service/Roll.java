package com.example.battlesimulator.service;

import org.springframework.data.util.Pair;

public class Roll {

    private Pair<Integer, Integer> roll;

    public Roll(int pcs, int sides) {
        this.roll = Pair.of(pcs, sides);
    }

    public int getPcs() {
        return roll.getFirst();
    }

    public int getSides() {
        return roll.getSecond();
    }
}
