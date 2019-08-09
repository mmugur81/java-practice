package practice;

import java.io.Serializable;
import java.util.*;

public class ChallengeOfWinterFell {
    public static void main(String args[]) throws Exception {
        String result = run("Seven Kingdom practice.Army", 4, 5);
        System.out.println(result);
    }

    public static String run(String first_strike_army_name, int no_of_dragons, int no_of_white_lords) {
        Army army1 = new Army("Seven Kingdom practice.Army");
        //army1.addUnits(new practice.Dragon(), no_of_dragons);
        //army1.addUnits(new practice.Infantry(), 5000);
        army1.addUnits(new God(), 1);

        Army army2 = new Army("White Walker practice.Army");
        army2.addUnits(new WhiteLord(), no_of_white_lords);
        army2.addUnits(new Ghoul(), 10000);//10000

        ArmyFight fight;
        if (first_strike_army_name.equals(army1.getName())) {
            fight = new ArmyFight(army1, army2);
        } else {
            fight = new ArmyFight(army2, army1);
        }

        fight.fight();

        return fight.getWinner() + "|" + fight.getTurns();
    }
}


class ArmyUnit {
    private final int damage;
    private final int defence;

    private int currentDamage;
    private int currentDefence;

    public ArmyUnit(int damage, int defence) {
        this.damage = damage;
        this.defence = defence;
        resetForTurn();
    }

    public void resetForTurn() {
        currentDamage = damage;
        currentDefence = defence;
    }

    public boolean canAttack(ArmyUnit that) {
        return this.currentDamage >= that.currentDefence;
    }

    public int attack(ArmyUnit that) {
        int attackPoints = Math.min(this.currentDamage, that.currentDefence);
        this.currentDamage -= attackPoints;
        that.currentDefence -= attackPoints;

        return attackPoints;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public int getCurrentDefence() {
        return currentDefence;
    }
}

class God extends ArmyUnit {
    public God() {
        super(30000, 251);
    }
}

class Dragon extends ArmyUnit {
    public Dragon() {
        super(600, 600);
    }
}

class Infantry extends ArmyUnit {
    public Infantry() {
        super(2, 2);
    }
}

class WhiteLord extends ArmyUnit {
    public WhiteLord() {
        super(50, 100);
    }
}

class Ghoul extends ArmyUnit {
    public Ghoul() {
        super(1, 3);
    }
}

class Army {
    private Map<ArmyUnit, Integer> units = new HashMap<>();
    private Map<ArmyUnit, Integer> damage = new HashMap<>();
    private Map<ArmyUnit, Integer> defence = new HashMap<>();

    private String name;
    private int totalDamage;
    private int totalDefence;

    public Army(String name) {
        this.name = name;
    }

    public void addUnits(ArmyUnit unit, Integer quantity) {
        if (units.containsKey(unit)) {
            units.compute(unit, (u, v) -> v + quantity);
        } else {
            units.put(unit, quantity);
        }

        updateStats();
    }

    public void updateStats() {
        // Update army damage & defence
        totalDamage = 0;
        totalDefence = 0;

        for (Map.Entry<ArmyUnit, Integer> unitQt : units.entrySet()) {
            ArmyUnit unit = unitQt.getKey();
            Integer quantity = this.units.get(unit);

            damage.put(unit, quantity * unit.getCurrentDamage());
            totalDamage += quantity * unit.getCurrentDamage();

            defence.put(unit, quantity * unit.getCurrentDefence());
            totalDefence += quantity * unit.getCurrentDefence();
        }
    }

    public boolean attack(Army that) {
        //sort both by damage descending
        this.sortByDamage(true); //DESC
        that.sortByDamage(true); //DESC

        int realDamage = 0;
        int initialDamage2 = that.totalDamage;
        int initialDefence2 = that.totalDefence;


        for (Map.Entry<ArmyUnit, Integer> unitQt1 : this.damage.entrySet()) {
            ArmyUnit unit1 = unitQt1.getKey();
            Integer q1 = this.units.get(unit1);
            Integer totalDamage = unitQt1.getValue();

            if (totalDamage <= 0) {
                continue;
            }

            for (Map.Entry<ArmyUnit, Integer> unitQt2 : that.defence.entrySet()) {
                ArmyUnit unit2 = unitQt2.getKey();
                Integer q2 = that.units.get(unit2);
                if (q2 <= 0) {
                    continue;
                }
                Integer totalDefence = unitQt2.getValue();

                // Compute how many strikes can perform
                int actualDamage = Math.min(totalDamage, totalDefence);
                int kills = actualDamage / unit2.getCurrentDefence();
                actualDamage = kills * unit2.getCurrentDefence();
                realDamage += actualDamage;

                //update second army
                that.units.compute(unit2, (u, v) -> v - kills);
                that.updateStats();

                //todo: compute also the reminder

                totalDamage -= actualDamage;

                //update first army
                /*int exhausted = actualDamage / unit1.getCurrentDamage();
                this.units.compute(unit1, (u, v) -> v - exhausted);
                this.updateStats();*/
            }
        }

        System.out.println(String.format("%s A:%d D:%d   -> %d ->   %s A:%d D:%d",
                this.name, this.totalDamage, this.totalDefence,
                realDamage,
                that.name, initialDamage2, initialDefence2));

        //returns if fight over
        return that.totalDefence <= 0;
    }

    public String getName() {
        return name;
    }

    public boolean stillStanding() {
        return totalDefence > 0;
    }

    private void sortByDamage(boolean desc) {
        damage = sortByValue(damage, desc);
    }

    private void sortByDefence(boolean desc) {
        defence = sortByValue(defence, desc);
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean desc) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> (desc ? -1 : 1) * c1.getValue().compareTo(c2.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}

class ArmyFight {
    private final Army first;
    private final Army second;

    int turns = 0;
    String winner = "";

    ArmyFight(Army first, Army second) {
        this.first = first;
        this.second = second;
    }

    public void fight() {
        boolean fightOver = false;

        while (!fightOver) {
            turns++;
            System.out.println("\nTurn [" + turns + "]");

            //start attacking
            fightOver = first.attack(second);
            if (!fightOver) {
                fightOver = second.attack(first);
            }
        }

        winner = first.stillStanding() ? first.getName() : second.getName();
    }

    public int getTurns() {
        return turns;
    }

    public String getWinner() {
        return winner;
    }
}