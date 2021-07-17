package up.info.tp3.data;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private String name;
    private String type;
    private int height;
    private int baseXP;
    private int weight;
    private int HP;
    private int defence;
    private int speDefence;
    private int attack;
    private int speAttack;
    private int speed;


    public Pokemon(String name, String type, int height, int baseXP, int weight, int HP, int defence, int speDefence, int attack, int speAttack, int speed) {
        this.name = name;
        this.speed = speed;
        this.type = type;
        this.height = height;
        this.baseXP = baseXP;
        this.weight = weight;
        this.HP = HP;
        this.defence = defence;
        this.speDefence = speDefence;
        this.attack = attack;
        this.speAttack = speAttack;
    }

    public Pokemon(String name, String type, int height, int baseXP, int weight) {
        this.name = name;
        this.speed = speed;
        this.type = type;
        this.height = height;
        this.baseXP = baseXP;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getBaseXP() {
        return baseXP;
    }

    public int getWeight() {
        return weight;
    }

    public int getHP() {
        return HP;
    }

    public int getDefence() {
        return defence;
    }

    public int getSpeDefence() {
        return speDefence;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpeAttack() {
        return speAttack;
    }
}
