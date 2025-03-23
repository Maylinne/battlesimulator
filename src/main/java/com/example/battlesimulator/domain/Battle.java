package com.example.battlesimulator.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Battle.class);
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
        checkParticipants(participants);
        participants.forEach(Player::lockForBattle);

        while (continueBattle) {
            Round round = new Round(participants);
            round.execute();
            rounds.add(round);

            continueBattle = askForAnotherRound();
        }

        saveBattleResults();
    }

    private void checkParticipants(List<Player> participants) {
        for (Player player : participants) {
            if (player.isLockedForBattle()) {
                LOGGER.info("Player {} is currently locked for battle. He cannot join it now.", player.getName());
            }
        }
    }

    private boolean askForAnotherRound() {
        System.out.print("Do you want another round? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    private void saveBattleResults() {
        LOGGER.info("Battle over! Saving results...");
        for (Player player : participants) {
            player.unLockForBattle();
            LOGGER.info("{} - Health: {}, Fatigue: {}",
                    player.getName(), player.getHealthPoint(), player.getFatiguePoint());
        }
    }
}
