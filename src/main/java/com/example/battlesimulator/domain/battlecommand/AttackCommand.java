package com.example.battlesimulator.domain.battlecommand;

import com.example.battlesimulator.domain.Player;
import com.example.battlesimulator.domain.battlecommand.BattleCommand;
import org.springframework.data.util.Pair;

public class AttackCommand implements BattleCommand {

    private final Player attacker;
    private final Player defender;
    private final int attackRoll;
    private final Pair<Integer, Integer> bodyRoll;

    public AttackCommand(Player attacker, Player defender, int attackRoll, Pair<Integer, Integer> bodyRoll) {
        this.attacker = attacker;
        this.defender = defender;
        this.attackRoll = attackRoll;
        this.bodyRoll = bodyRoll;
    }

    @Override
    public void execute() {
        // Mock logic
        int damage = Math.max(0, attacker.getBaseAttack() + attackRoll - defender.getBaseDefense());
        System.out.printf("%s attacked %s on the %d, %d for %d damage!%n",
                attacker.getName(), defender.getName(), bodyRoll.getFirst(), bodyRoll.getSecond(), damage);
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public int getAttackRoll() {
        return attackRoll;
    }

    public Pair<Integer, Integer> getBodyRoll() {
        return bodyRoll;
    }
}
