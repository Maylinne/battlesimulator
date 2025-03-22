package com.example.battlesimulator.domain;

import com.example.battlesimulator.domain.battlecommand.AttackCommand;
import com.example.battlesimulator.domain.battlecommand.BattleCommand;
import org.springframework.data.util.Pair;

import java.util.*;

import static com.example.battlesimulator.service.DiceRoller.roll;

public class Round {
    private final List<Player> participants;
    private final Map<Player, Integer> turnOrder = new HashMap<>();

    private final Random random = new Random();

    public Round(List<Player> participants) {
        this.participants = new ArrayList<>(participants);
    }

    public void prepare() {
        for (Player participant : participants) {
            turnOrder.put(participant, rollInitiative(participant));
        }
    }

    public void execute() {
        while (true) {
            // Sort by initiative (descending)
            LinkedHashMap<Player, Integer> sortedInitiatives = setUpTurnOrder();

            // Get the first player (highest initiative)
            Player currentPlayer = getFirstPlayer(sortedInitiatives);

            // Check if the highest initiative is below 10 — end round
            int currentInitiative = sortedInitiatives.get(currentPlayer);
            if (currentInitiative < 10) break;

            // Execute the player's move
            BattleCommand command = setUpAttackCommand(currentPlayer);
            command.execute();

            // Reduce initiative and re-sort
            turnOrder.put(currentPlayer, currentInitiative - 10);
        }
    }

    private Player getFirstPlayer(LinkedHashMap<Player, Integer> sortedInitiatives) {
        return sortedInitiatives.entrySet().iterator().next().getKey();
    }

    private LinkedHashMap<Player, Integer> setUpTurnOrder() {
        return turnOrder.entrySet()
                .stream()
                .sorted(Map.Entry.<Player, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
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
