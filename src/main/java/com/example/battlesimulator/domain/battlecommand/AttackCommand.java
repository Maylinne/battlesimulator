package com.example.battlesimulator.domain.battlecommand;

import com.example.battlesimulator.domain.Player;
import com.example.battlesimulator.domain.Weapon;
import com.example.battlesimulator.service.DiceRoller;
import com.example.battlesimulator.service.Roll;

public class AttackCommand implements BattleCommand {

    private final Player attacker;
    private final Player defender;
    private final int attackRoll;
    private final Roll bodyRoll;
    private final Weapon weapon;
    private final int damage;

    public AttackCommand(Player attacker, Player defender, int attackRoll, Roll bodyRoll, Weapon weapon, int damage) {
        this.attacker = attacker;
        this.defender = defender;
        this.attackRoll = attackRoll;
        this.bodyRoll = bodyRoll;
        this.weapon = weapon;
        this.damage = damage;
    }

    @Override
    public void execute() {
        int attackValue = attacker.getAttack() + attackRoll;
        int defenseValue = defender.getDefense();
        if (attackValue > defenseValue) {
            if (attackValue - defenseValue >= 50) {
                calculateOverhit();
            }  else {
                calculateDamage();
            }
        }

        int damage = Math.max(0, attacker.getBaseAttack() + attackRoll - defender.getBaseDefense());
        System.out.printf("%s attacked %s on the %d, %d for %d damage!%n",
                attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), damage);
    }

    private void calculateDamage() {
        int hp = damage % weapon.getConversion();
        int fp = damage - weapon.getConversion() * 7;

        if (defender.getHealthPoint() <= hp) {
            defenderDies();
        } else {
            defenderHpDamage(hp);
            if (defender.getFatiguePoint() <= fp) {
                if (isPassingOut()) {
                    defenderPassingOut();
                } else {
                    defenderFpDamage(fp);
                }
            }
        }
    }

    private void defenderFpDamage(int fp) {
        defender.adjustFatiguePoint(-fp);
        System.out.printf("%s hit %s on %d, %d with %d fp.%n ",
                attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), fp);
    }

    private void defenderHpDamage(int hp) {
        defender.adjustHealthPoint(-hp);
        System.out.printf("%s hit %s on %d, %d with %d hp.%n ",
                attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), hp);
    }

    private void defenderPassingOut() {
        defender.setFatiguePoint(0);
        System.out.printf("%s hit %s on %d, %d with %d fp.%n %s passed out in the result.",
                attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), damage, defender.getName());
    }

    private boolean isPassingOut() {
        return defender.getWillpower() > DiceRoller.roll(1, 10) + 10;
    }

    private void calculateOverhit() {
        if (defender.getHealthPoint() <= damage) {
            defenderDies();
        } else {
            defender.adjustHealthPoint(damage);
            System.out.printf("%s hit %s on %d, %d with %d hp.%n",
                    attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), damage);
        }
    }

    private void defenderDies() {
        defender.setHealthPoint(0);
        System.out.printf("%s hit %s on %d, %d with %d hp.%n %s died in the result.",
                attacker.getName(), defender.getName(), bodyRoll.getPcs(), bodyRoll.getSides(), damage, defender.getName());
    }

    // region getters
    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public int getAttackRoll() {
        return attackRoll;
    }

    public Roll getBodyRoll() {
        return bodyRoll;
    }

    // endregion
}
