package hexaround.entity;

import hexaround.config.CreatureDefinition;
import hexaround.config.PlayerConfiguration;

import java.util.Collection;
import java.util.Set;

public class Board {

    protected Set<Player> players;

    boolean firstMove = false;
    boolean secondMove = false;

    boolean BlueTurn = true;

    int moveCount = 0;

    public Board(Set<Player> players) {
        this.players = players;
    }

    public Creature getCreatureAt(int x, int y) {
        for (final Player player: getPlayers()) {
            Creature creature = player.getCreatureAt(x,y);
            if (creature!=null) {
                return creature;
            }
        }
        return null;
    }

    public Set<Player> getPlayers() {
        return players;
    }


    public Player getCorrectPlayer() {
        PlayerName pName;
        Player returnPlayer = null;
        if (BlueTurn == true) {
            pName = PlayerName.BLUE;
        } else {
            pName = PlayerName.RED;
        }
        for (final Player player: getPlayers()) {
            if (player.name.equals(pName)) {
                returnPlayer = player;
            }
        }
        return returnPlayer;
    }

    public void switchPlayerTurn() {
        BlueTurn = !BlueTurn;
        moveCount++;
    }
    public int getMoveCount() {return moveCount;}


    /**
     *  Used for placing a creature
     * @param creatureName
     * @param x
     * @param y
     * @return the Creature if it was placed, else returns null
     */
    public Creature placeCreature(CreatureName creatureName, int x, int y) {
        Player player = getCorrectPlayer();
            //loop through players
                for (Creature creature : player.getCreatures()) {
                    if (creature.getName().equals(creatureName) && creature.getPlaced() == false) {//will now set the second butterfly
                        if (firstMove == false) {
                            creature.setLocation(x, y);
                            firstMove = true;
                            switchPlayerTurn();
                            return creature;
                        } else if (secondMove==false) {
                            if (AnyCreatureNextDoor(x,y)==true) {
                                secondMove=true;
                                creature.setLocation(x, y);
                                switchPlayerTurn();
                                return creature;
                            }
                        } else if (playerCreatureNextDoor(x, y) == true && otherplayerCreatureNextDoor(x,y) == false) {
                            creature.setLocation(x, y);
                            switchPlayerTurn();
                            return creature;
                        }
                    }
                }
        return null;
    }


    /**
     * Used to see if there is a creature of the same team next to the creature being placed with the method above
     * @param x
     * @param y
     * @return a boolean. True if thier is a creature of the same team next door or false otherwise
     */
    public boolean playerCreatureNextDoor(int x, int y) {
         Player player = getCorrectPlayer();//now only checking same player
            for (Creature creature : player.getCreatures()) {
                if (creature.getXlocation() == x+1 && creature.getYlocation() == y) {
                    return true;
                } else if (creature.getXlocation() == x && creature.getYlocation() == y+1) {
                    return true;
                } else if (creature.getXlocation() == x-1 && creature.getYlocation() == y) {
                    return true;
                } else if (creature.getXlocation() == x && creature.getYlocation() == y-1) {
                    return true;
                } else if (creature.getXlocation() == x+1 && creature.getYlocation() == y-1) {
                    return true;
                } else if (creature.getXlocation() == x-1 && creature.getYlocation() == y+1) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Used in placeCreature method to see if the oppisite team's player's creatures is next to the location the creature will be placed
     * @param x
     * @param y
     * @return a boolean value, true if the oppisite team's creatures will be next door or false otherwise
     */
    public boolean otherplayerCreatureNextDoor(int x, int y) {
        for (Player player:getPlayers()) {
            if (player!=getCorrectPlayer()) {
                for (Creature creature : player.getCreatures()) {
                    if (creature.getXlocation() == x + 1 && creature.getYlocation() == y) {
                        return true;
                    } else if (creature.getXlocation() == x && creature.getYlocation() == y + 1) {
                        return true;
                    } else if (creature.getXlocation() == x - 1 && creature.getYlocation() == y) {
                        return true;
                    } else if (creature.getXlocation() == x && creature.getYlocation() == y - 1) {
                        return true;
                    } else if (creature.getXlocation() == x + 1 && creature.getYlocation() == y - 1) {
                        return true;
                    } else if (creature.getXlocation() == x - 1 && creature.getYlocation() == y + 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Is used in the second move of the game in the function placeCreature
     * @param x
     * @param y
     * @return boolan if there is a creature next door to the location the player is trying to place their creature
     */
    public boolean AnyCreatureNextDoor(int x, int y) {
        for (Player player : getPlayers()) {//now only checking same player
            for (Creature creature : player.getCreatures()) {
                if (creature.getXlocation() == x + 1 && creature.getYlocation() == y) {
                    return true;
                } else if (creature.getXlocation() == x && creature.getYlocation() == y + 1) {
                    return true;
                } else if (creature.getXlocation() == x - 1 && creature.getYlocation() == y) {
                    return true;
                } else if (creature.getXlocation() == x && creature.getYlocation() == y - 1) {
                    return true;
                } else if (creature.getXlocation() == x + 1 && creature.getYlocation() == y - 1) {
                    return true;
                } else if (creature.getXlocation() == x - 1 && creature.getYlocation() == y + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the number of neighbors a hexagon has
     * @param x
     * @param y
     * @return a integer value
     */
    public int getNumberOfNeighbors(int x, int y) {
        int num = 0;
        if (getCreatureAt(x+1,y)!=null) {
            num++;
        }
        if (getCreatureAt(x,y+1)!=null) {
            num++;
        }
        if (getCreatureAt(x-1,y)!=null) {
            num++;
        }
        if (getCreatureAt(x,y-1)!=null) {
            num++;
        }
        if (getCreatureAt(x+1,y-1)!=null) {
            num++;
        }
        if (getCreatureAt(x-1,y+1)!=null) {
            num++;
        }
        return num;
    }

    /**
     * function used to determine if the game is over
     * @return the PlayerName of the player who lost
     */
    public PlayerName gameOver() {

        Creature butterfly = null;
        for (Player player:getPlayers()) {
        for (Creature creature:player.getCreatures()) {
            if (creature.name==CreatureName.BUTTERFLY) {//getting the one butterfly
                butterfly = creature;
            }
        }
        if (butterfly.placed) {
            if (getNumberOfNeighbors(butterfly.getXlocation(),butterfly.getYlocation())==6) {
                return butterfly.getPlayer().name; //this is the losing player name
            }
        }
        }
        return null;
    }

}


