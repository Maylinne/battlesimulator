package com.example.battlesimulator.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    private List<Player> participants;

    @BeforeEach
    void setUp() {
        Player player1 = new Player.Builder("Legolas").initiative(40).build();
        Player player2 = new Player.Builder("Aragorn").initiative(20).build();
        Player player3 = new Player.Builder("Goblin").initiative(15).build();

        participants = List.of(player1, player2, player3);
    }

    @Test
    void roundShouldExecuteUntilNoPlayerHasInitiativeAbove10() {
        Round round = new Round(participants);
        round.execute();

        // After the round ends, no player's initiative should be 10 or higher
        assertTrue(allInitiativesBelowThreshold(participants),
                "All players should have initiative below 10 at round end");
    }

    @Test
    void allPlayersShouldHaveInitiativeBelow10AtEndOfRound() {
        Round round = new Round(participants);
        round.execute();

        for (Player player : participants) {
            int initiative = player.getInitiative();
            assertTrue(initiative < 10,
                    player.getName() + "'s initiative should be below 10 after the round ends. Was: " + initiative);
        }
    }

    private boolean allInitiativesBelowThreshold(List<Player> players) {
        return players.stream().allMatch(p -> p.getInitiative() < 10);
    }
}
