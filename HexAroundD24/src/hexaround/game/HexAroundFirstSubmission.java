package hexaround.game;

import hexaround.entity.*;
import hexaround.movement.MoveResponse;
import hexaround.movement.MoveResult;
import hexaround.movement.*;
import java.util.List;
public class HexAroundFirstSubmission implements IHexAround1{

    private Board board; //instance in game builder



    public void setBoard(Board board) {
        this.board=board;
    }


    /**
     * This is the default constructor, and the only constructor
     * that you can use. The builder creates an instance using
     * this connector. You should add getters and setters as
     * necessary for any instance variables that you create and
     * will be filled in by the builder.
     */
    public HexAroundFirstSubmission() {
        // Nothing to do.
    }

    /**
     * Given the x and y-coordinates for a hex, return the name
     * of the creature on that coordinate.
     * @param x
     * @param y
     * @return the name of the creature on (x, y), or null if there
     *  is no creature.
     */
    @Override
    public CreatureName getCreatureAt(int x, int y) {
        CreatureName returnCN = null;
        final Creature creature = board.getCreatureAt(x,y);
        if (creature!=null) {
            returnCN = creature.getName();
        }
        return returnCN;
    }

    /**
     * Determine if the creature at the x and y-coordinates has the specified
     * property. You can assume that there will be a creature at the specified
     * location.
     * @param x
     * @param y
     * @param property the property to look for.
     * @return true if the creature at (x, y) has the specified property,
     *  false otherwise.
     */
    @Override
    public boolean hasProperty(int x, int y, CreatureProperty property) {
        final Creature creature = board.getCreatureAt(x,y);
        if (creature!=null) {
            for (CreatureProperty oneProperty: creature.getProperties()) {
                if (oneProperty.equals(property)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Given the x and y-coordinate of a hex, determine if there is a
     * piece on that hex on the board.
     * @param x
     * @param y
     * @return true if there is a piece on the hex, false otherwise.
     */
    @Override
    public boolean isOccupied(int x, int y) {
        final Creature creature = board.getCreatureAt(x,y);
        if (creature!=null) {
            return true;
        }
        return false;
    }

    /**
     * Given the coordinates for two hexes, (x1, y1) and (x2, y2),
     * return whether the piece at (x1, y1) could reach the other
     * hex.
     * You can assume that there will be a piece at (x1, y1).
     * The distance is just the distance between the two hexes. You
     * do not have to do any other checking.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return itrue if the distance between the two hexes is less
     * than or equal to the maximum distance property for the piece
     * at (x1, y1). Return false otherwise.
     */
    @Override
    public boolean canReach(int x1, int y1, int x2, int y2) {
        Creature creature = board.getCreatureAt(x1,y1);
        if (creature!=null) {
            if (creature.canReach(x2,y2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * For this submission, just put the piece on the board. You
     * can assume that the hex (x, y) is empty. You do not have to do
     * any checking.
     * @param creature
     * @param x
     * @param y
     * @return a response, or null. It is not going to be checked.
     */
    @Override
    public MoveResponse placeCreature(CreatureName creature, int x, int y) {
        if (!(creature.equals(CreatureName.BUTTERFLY)) && board.getMoveCount()>6 && board.getCorrectPlayer().getButterfly().getPlaced()==false) {
            return new MoveResponse(MoveResult.MOVE_ERROR,  "Butterfly not placed, try placing the butterfly");
        }

        if (board.getCreatureAt(x,y)==null) {
            Creature placedCreature = board.placeCreature(creature,x,y);
            if (placedCreature!=null) { //placed creature on board
                //check to see if game is over
                if (board.gameOver()!=null) {
                    if (board.gameOver()== PlayerName.BLUE) {
                        return new MoveResponse(MoveResult.RED_WON, "Red won");
                    } else {
                        return new MoveResponse(MoveResult.BLUE_WON, "Blue won");
                    }
                }
                //gotta check that butterfly is placed depending on amount of turns.
                return new MoveResponse(MoveResult.OK, "Legal move");
            }
        }
        if (board.getCorrectPlayer().creatureFound(creature)==false) {//name not found {
            return new MoveResponse(MoveResult.MOVE_ERROR, "Creature name not found in Player list");
        }
        return new MoveResponse(MoveResult.MOVE_ERROR, "Can't place creature");
    }

    /**
     * @param creature
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    @Override
    public MoveResponse moveCreature(CreatureName creature, int fromX, int fromY, int toX, int toY) {
        if (board.getMoveCount()>6 && board.getCorrectPlayer().getButterfly().getPlaced()==false) {
            return new MoveResponse(MoveResult.MOVE_ERROR,  "Butterfly not placed, try placing the butterfly");
        }


        //gotta check that butterfly is placed depending on amount of turns.

        boolean correctPlayerWithCreature = false;
        Creature Entirecreature = board.getCreatureAt(fromX,fromY);
        if (Entirecreature!=null) {
            if (Entirecreature.getPlayer().getName().toString().equals(board.getCorrectPlayer().getName().toString())) {
                correctPlayerWithCreature = true;
            }
        }

        if (correctPlayerWithCreature&& Entirecreature.propertyFound(CreatureProperty.KAMIKAZE)==false && board.getCreatureAt(toX,toY)!=null) {
            return  new MoveResponse(MoveResult.MOVE_ERROR,  "The destination had a creature there and the one you were moving isn't a kamikaze");
        }


        MoveResponse response = new MoveResponse(MoveResult.MOVE_ERROR,  "Error not accounted for");;
        if (correctPlayerWithCreature && Entirecreature.canReach(toX,toY)&& Entirecreature.propertyFound(CreatureProperty.JUMPING)) {
            Search search = new Search();
            boolean jumpCorrect = search.jumpHandle(board,Entirecreature,fromX,fromY,toX,toY);
            boolean connected = search.wholeColonyConnected(board,fromX,fromY,toX,toY);
            if (jumpCorrect && connected) {
                response = new MoveResponse(MoveResult.OK,  "Legal move");
                if (Entirecreature.propertyFound(CreatureProperty.KAMIKAZE) && board.getCreatureAt(toX,toY)!=null) {
                    Creature creatureChange = board.getCreatureAt(toX,toY);
                    creatureChange.changePlaced(false);
                    board.getCorrectPlayer().removeCreature(Entirecreature);
                } else {
                    Entirecreature.setLocation(toX, toY);
                }
                board.switchPlayerTurn();
            } else if (connected==false) {
                response = new MoveResponse(MoveResult.MOVE_ERROR,  "Colony is not connected, try again");
            }else if (jumpCorrect==false){
                response = new MoveResponse(MoveResult.MOVE_ERROR,  "the jump wasn't in a line or the end point had a creature on it");
            }
        } else if (correctPlayerWithCreature && Entirecreature.canReach(toX,toY)) {//check if spot is open, animal can reach, and connected to colony
            //added
            Search search = new Search();
            //boolean good = search.BFS(true, board,fromX,fromY,toX,toY,0, Entirecreature); //added

            List<Node> list = search.BFS2(board,Entirecreature,fromX,fromY,toX,toY);
            if (list!=null) {
                if (list.size()-1<=Entirecreature.getMaxDistance()) {
                    boolean connected = search.wholeColonyConnected(board,fromX,fromY,toX,toY);
                    if (connected==true) {
                        response = new MoveResponse(MoveResult.OK,  "Legal move");
                        if (Entirecreature.propertyFound(CreatureProperty.KAMIKAZE) && board.getCreatureAt(toX,toY)!=null) {
                            Creature creatureChange = board.getCreatureAt(toX,toY);
                            creatureChange.changePlaced(false);
                            board.getCorrectPlayer().removeCreature(Entirecreature);
                        } else {
                            Entirecreature.setLocation(toX, toY);
                        }
                        board.switchPlayerTurn();
                    } else {
                        response = new MoveResponse(MoveResult.MOVE_ERROR,  "Colony is not connected, try again");
                    }
                } else {
                    response = new MoveResponse(MoveResult.MOVE_ERROR,  "The distance is farther than the animal can travel");
                }
            } else {
                response = new MoveResponse(MoveResult.MOVE_ERROR, "BFS2 returned null, probably draggability no work");
            }
        } else if (correctPlayerWithCreature== false) {
            //response = new MoveResponse(MoveResult.MOVE_ERROR,  "Player does not have creature listed, creature's owner" + Entirecreature.getPlayer().getName().toString() + " player trying to go "+board.getCorrectPlayer().getName().toString());
            response = new MoveResponse(MoveResult.MOVE_ERROR,  "Player does not have creature listed or the incorrect turn is being taken");
        }
          else if (Entirecreature.canReach(toX,toY)==false) {
            response = new MoveResponse(MoveResult.MOVE_ERROR,  "Creature can't reach location");
        }
        //check to see if the game is over or not
        if (board.gameOver()!=null) {
            if (board.gameOver()== PlayerName.BLUE) {
                return new MoveResponse(MoveResult.RED_WON, "Red won");
            } else {
                return new MoveResponse(MoveResult.BLUE_WON, "Blue won");
            }
        }
        return response;
    }
}
