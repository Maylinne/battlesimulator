package com.example.battlesimulator.service;

import java.util.Random;

public class DiceRoller {

    public static int roll(int pcs, int sides) {
        Random random = new Random();
        int result = 0;
        for (int i = 0; i < pcs; i++) {
            result += random.nextInt(10) + 1;
        }
        return result;
    }

    public static int roll(Roll roll) {
        return roll(roll.getPcs(), roll.getSides());
    }
}
