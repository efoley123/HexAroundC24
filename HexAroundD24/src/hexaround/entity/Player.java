package hexaround.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player {
    protected final PlayerName name;
    protected Set<Creature> creatures = new HashSet<>();//all creatures it has?

    protected final Map<CreatureName, Integer> allCreatures; //

    public Player(final PlayerName name, final Map<CreatureName, Integer> allCreatures) {
        this.name=name;
        this.allCreatures = allCreatures;
    }


    public Map<CreatureName, Integer>  getAllCreatures() {
        return allCreatures;
    }

    //getter
    public Creature getCreatureAt(int x, int y) {
        for (Creature creature: getCreatures()) {
            if (creature.getXlocation()==x && creature.getYlocation()==y) {
                return creature;
            }
        }
        return null;
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public Set<Creature> getCreatures() {
        return creatures;
    }

    public Creature getButterfly() {
        Creature returnCreature = null;
        for (Creature creature:getCreatures()) {
            if (creature.getName().equals(CreatureName.BUTTERFLY)) {
                returnCreature= creature;
            }
        }
       return returnCreature;
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }


    public boolean creatureFound(CreatureName name) {
        for (Creature creature:getCreatures()) {
            if (creature.getName()==name) {
                return true;
            }
        }
        return false;
    }


    public PlayerName getName() {
        return name;
    }


}
