package com.example.battlesimulator.domain;

import com.example.battlesimulator.domain.battlecommand.AttackCommand;
import com.example.battlesimulator.domain.battlecommand.BattleCommand;
import org.springframework.data.util.Pair;

import java.util.*;

import static com.example.battlesimulator.service.DiceRoller.roll;

public class Round {
    private final List<Player> participants;
    private final PriorityQueue<Player> turnOrder;

    private final Random random = new Random();

    public Round(List<Player> participants) {
        this.participants = new ArrayList<>(participants);
        participants.forEach(p -> p.setInitiative(rollInitiative(p)));
        // Initialize the priority queue with a comparator that sorts players by initiative in descending order
        turnOrder = new PriorityQueue<>(Comparator.comparingInt(Player::getInitiative).reversed());
        turnOrder.addAll(participants);
    }

    public void execute() {
        while (!turnOrder.isEmpty()) {
            Player currentPlayer = turnOrder.poll();
            if (currentPlayer.getInitiative() < 10) {
                break; // End the round if the highest initiative is below 10
            }
            // Execute the player's move
            BattleCommand command = setUpAttackCommand(currentPlayer);
            command.execute();
            // Adjust initiative and reinsert into the queue if still active
            currentPlayer.adjustInitiative(-10);
            if (currentPlayer.getInitiative() > 0) {
                turnOrder.add(currentPlayer);
            }
        }
    }

    private Player chooseDefender(Player currentPlayer) {
        List<Player> defenders = participants.stream()
                .filter(player -> !player.equals(currentPlayer))
                .toList();
        return defenders.get(random.nextInt(defenders.size()));
    }

    private int rollInitiative(Player player) {
        return player.getBaseInitiative() + roll(1, 10);
    }

    private AttackCommand setUpAttackCommand(Player currentPlayer) {
        Player defender = chooseDefender(currentPlayer);
        int attackRoll = roll(3, 6);
        Pair<Integer, Integer> bodyRoll = Pair.of(roll(1, 10), roll(1, 10));
        return new AttackCommand(currentPlayer, defender, attackRoll, bodyRoll);
    }
}
