package hexaround.entity;


import java.util.Collection;

import static java.lang.Math.max;

public class Creature {

    protected final CreatureName name;
    protected final int maxDistance;
    protected Collection<CreatureProperty> properties;

    protected final Player player; // is the player they belong to

    protected boolean placed = false;



    protected int xlocation = 1000;
    protected int ylocation = 1000;


    public Creature(CreatureName name, Player player, int maxDistance, Collection<CreatureProperty> properties) {
        this.name = name;
        this.player =player;//added this so creature knows who it belongs to
        this.maxDistance = maxDistance; //this isn't actually checking for the correct maxDistance
        this.properties = properties;
    }

    public void setLocation(int x,int y) {
        xlocation = x;
        ylocation = y;
        placed = true;
    }

    public boolean canReach(int newXlocation,int newYlocation) {
    //is this based on how they move and maxDistance
        double distance = Math.abs(Math.sqrt((newXlocation-xlocation)*(newXlocation-xlocation)+(newYlocation-ylocation)*(newYlocation-ylocation)));
        if (distance<=maxDistance) {
            return true;
        }
        return false;
    }


    public CreatureName getName() {
        return name;
    }

    public boolean getPlaced() {
        return placed;
    }
    public void changePlaced(boolean bool) {
        this.placed = bool;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public Collection<CreatureProperty> getProperties() {
        return properties;
    }

    public Player getPlayer() {
        return player;
    }

    public int getXlocation() {
        return xlocation;
    }

    public int getYlocation() {
        return ylocation;
    }


    /**
     * used to see if the input property is one that the creature has
     * @param property
     * @return
     */
    public boolean propertyFound(CreatureProperty property) {
        for (CreatureProperty propertyThere:getProperties()) {
            if (propertyThere.equals(property)) {
                return true;
            }
        }
        return false;
    }



}
