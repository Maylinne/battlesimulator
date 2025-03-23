package com.example.battlesimulator.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(3)
    @Max(20)
    private int strength = 12;

    @Min(3)
    @Max(20)
    private int agility = 12;

    @Min(3)
    @Max(20)
    private int speed = 12;

    @Min(3)
    @Max(20)
    private int stamina = 12;

    @Min(3)
    @Max(20)
    private int health = 12;

    @Min(3)
    @Max(20)
    private int willpower = 12;

    @Min(3)
    @Max(20)
    private int perception = 12;

    private boolean lockedForBattle;

    @Transient
    private int baseInitiative;
    @Transient
    private int initiative;

    @Transient
    private int baseAttack;

    @Transient
    private int baseDefense;

    @Transient
    private int healthPoint;

    @Transient
    private int fatiguePoint;

    // Private constructor — force builder usage
    private Player() {}

    // region Getters and setters...

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStamina() {
        return stamina;
    }

    public int getHealth() {
        return health;
    }

    public int getWillpower() {
        return willpower;
    }

    public int getPerception() {
        return perception;
    }

    // endregion

    public int getBaseInitiative() {
        return speed + perception;
    }
    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void adjustInitiative(int delta) {
        this.initiative += delta;
    }

    public int getBaseAttack() {
        return strength + agility + speed;
    }

    public int getBaseDefense() {
        return agility + speed + 75;
    }

    public int getHealthPoint() {
        return health;
    }

    public int getFatiguePoint() {
        return stamina + willpower;
    }

    public boolean isLockedForBattle() {
        return lockedForBattle;
    }

    public void lockForBattle() {
        this.lockedForBattle = true;
    }
    public void unLockForBattle() {
        this.lockedForBattle = false;
    }

    // Inner Builder
    public static class Builder {
        private final String name;
        private int strength = 12;
        private int agility = 12;
        private int speed = 12;
        private int stamina = 12;
        private int health = 12;
        private int willpower = 12;
        private int perception = 12;
        private int initiative = 12;

        public Builder(String name) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Player name cannot be null or blank.");
            }
            this.name = name;
        }

        public Builder strength(int strength) {
            this.strength = strength;
            return this;
        }

        public Builder agility(int agility) {
            this.agility = agility;
            return this;
        }

        public Builder speed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder stamina(int stamina) {
            this.stamina = stamina;
            return this;
        }

        public Builder health(int health) {
            this.health = health;
            return this;
        }

        public Builder willpower(int willpower) {
            this.willpower = willpower;
            return this;
        }

        public Builder perception(int perception) {
            this.perception = perception;
            return this;
        }
        public Builder initiative(int initiative) {
            this.initiative = initiative;
            return this;
        }

        public Player build() {
            Player player = new Player();
            player.name = this.name;
            player.strength = this.strength;
            player.agility = this.agility;
            player.speed = this.speed;
            player.stamina = this.stamina;
            player.health = this.health;
            player.willpower = this.willpower;
            player.perception = this.perception;
            player.initiative = this.initiative;

            // Validation with Jakarta Validator
            jakarta.validation.Validator validator = jakarta.validation.Validation
                    .buildDefaultValidatorFactory()
                    .getValidator();

            var violations = validator.validate(player);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder("Validation failed:\n");
                violations.forEach(v -> sb.append("- ").append(v.getPropertyPath())
                        .append(": ").append(v.getMessage()).append("\n"));
                throw new IllegalArgumentException(sb.toString());
            }

            return player;
        }
    }
}
