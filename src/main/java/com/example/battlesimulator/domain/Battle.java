package com.example.battlesimulator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battle {
    private final List<Player> participants;
    private final List<Round> rounds;
    private final Scanner scanner;

    public Battle(List<Player> participants) {
        this.participants = participants;
        this.rounds = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Battle begins!");
        boolean continueBattle = true;

        participants.forEach(Player::lockForBattle);

        while (continueBattle) {
            Round round = new Round(participants);
            round.execute();
            rounds.add(round);

            continueBattle = askForAnotherRound();
        }

        saveBattleResults();
    }

    private boolean askForAnotherRound() {
        System.out.print("Do you want another round? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    private void saveBattleResults() {
        System.out.println("Battle over! Saving results...");
        for (Player player : participants) {
            player.setLockedForBattle(false);
            System.out.printf("%s - Health: %d, Fatigue: %d%n",
                    player.getName(), player.getHealthPoint(), player.getFatiguePoint());
        }
    }
}
