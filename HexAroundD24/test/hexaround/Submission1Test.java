package hexaround;

import hexaround.entity.CreatureProperty;
import hexaround.game.*;
import hexaround.movement.MoveResponse;
import hexaround.movement.MoveResult;
import org.junit.jupiter.api.*;

import java.io.*;

import static hexaround.entity.CreatureName.*;
import static org.junit.jupiter.api.Assertions.*;

public class Submission1Test {
    HexAroundFirstSubmission gameManager = null;

    @Test
    void firstTest() throws IOException {
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        gameManager.placeCreature(GRASSHOPPER, 5, 42);
        assertEquals(GRASSHOPPER, gameManager.getCreatureAt(5, 42));
    }

    @Test
    void hasProperty() throws IOException {
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        gameManager.placeCreature(GRASSHOPPER, 5, 42);
        assertEquals(true, gameManager.hasProperty(5, 42, CreatureProperty.JUMPING));
        assertEquals(true, gameManager.hasProperty(5, 42, CreatureProperty.INTRUDING));
        assertEquals(false, gameManager.hasProperty(5, 42, CreatureProperty.WALKING));
        assertEquals(false, gameManager.hasProperty(5, 42, CreatureProperty.QUEEN));
    }

    @Test
    void isOccupied() throws IOException {
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        gameManager.placeCreature(GRASSHOPPER, 0, 0);
        assertEquals(false, gameManager.isOccupied(5, 42));
        assertEquals(false, gameManager.isOccupied(1, 0));
        assertEquals(true, gameManager.isOccupied(0, 0));

    }

    @Test
    void canReach() throws IOException {
        //writing this
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        gameManager.placeCreature(GRASSHOPPER, 5, 42);
        assertEquals(false, gameManager.canReach(5, 42, 0, 0));
        assertEquals(false, gameManager.canReach(0, 0, 0, 0));
        assertEquals(true, gameManager.canReach(5, 42, 6, 42));
        assertEquals(false, gameManager.canReach(5, 42, 10, 42));
    }


    @Test
    void placeCreature() throws IOException {
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        MoveResponse response = gameManager.placeCreature(GRASSHOPPER, 5, 42);
        assertEquals(MoveResult.OK, response.moveResult());
        response = gameManager.placeCreature(BUTTERFLY, 5, 42);
        assertEquals(MoveResult.MOVE_ERROR, response.moveResult());
        assertEquals(GRASSHOPPER, gameManager.getCreatureAt(5, 42));
    }


    @Test
    void TestForMoveResponse() throws IOException {
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");
        gameManager.placeCreature(BUTTERFLY, 0, 0);//blue
        gameManager.placeCreature(GRASSHOPPER, 1, 0);//red
        gameManager.placeCreature(HORSE, -1, 1);//blue
        gameManager.placeCreature(DOVE, 2, 0);//red
        gameManager.placeCreature(CRAB, -1, 2); //blue
        gameManager.placeCreature(BUTTERFLY, 2, -1);//red
        MoveResponse mr = gameManager.moveCreature(CRAB, -1, 2, -2, 2); //blue
        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response.message(), mr.message());
        mr = gameManager.moveCreature(DOVE, 2, 0, 2, -2); //red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response.message(), mr.message());
        mr = gameManager.moveCreature(BUTTERFLY, 0, 0, 3, -3); //blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Creature can't reach location");
        assertEquals(response.message(), mr.message());
    }



    @Test
    void disconnectedMoveCreature() throws IOException { //testing right now
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        MoveResponse response = new MoveResponse(MoveResult.MOVE_ERROR, "Creature name not found in Player list");
        assertEquals(response,gameManager.placeCreature(CRAB, 1, 1)); //blue

        gameManager.placeCreature(GRASSHOPPER, 1, 1); //blue
        gameManager.placeCreature(GRASSHOPPER, 0, 2);//red
        gameManager.placeCreature(BUTTERFLY, 1, 0);//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Creature can't reach location");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2, 0, -2));//red
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Colony is not connected, try again");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2, 0, 3));//red
    }

    @Test
    void creatureMaxDistMoveCreature() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/FirstConfiguration.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/FirstConfiguration.hgc");
        MoveResponse response = new MoveResponse(MoveResult.MOVE_ERROR, "Creature name not found in Player list");
        assertEquals(response,gameManager.placeCreature(CRAB, 1, 1)); //blue

        gameManager.placeCreature(GRASSHOPPER, 1, 1); //blue
        gameManager.placeCreature(GRASSHOPPER, 0, 2);//red
        gameManager.placeCreature(BUTTERFLY, 1, 0);//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Can't place creature");
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, -2, 0)); //red
        gameManager.placeCreature(GRASSHOPPER, 2, 0); //red
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Player does not have creature listed or the incorrect turn is being taken");
        assertEquals(response, gameManager.moveCreature(BUTTERFLY, 1, 0, 0, 3));//blue
    }

    @Test
    void testingUptoLevel2Creature() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        gameManager.placeCreature(CRAB, 1, 1);//blue
        gameManager.placeCreature(GRASSHOPPER, 2, 0); //red
        gameManager.placeCreature(GRASSHOPPER, 0, 2);//blue
        gameManager.placeCreature(DOVE, 3, 0);//red
        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, 0, 1)); //blue
        gameManager.placeCreature(GRASSHOPPER, 2, -1); //red
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Butterfly not placed, try placing the butterfly");
        //testing flying
        assertEquals(response, gameManager.moveCreature(DOVE, 3, 0,1,0));//red
        assertEquals(response, gameManager.placeCreature(DOVE, 5, 0));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(BUTTERFLY, 3, -1)); //red
        gameManager.placeCreature(CRAB, -1, 2); //blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(DOVE, 3, 0,1,0));//red

        //testing jumping
        response = new MoveResponse(MoveResult.MOVE_ERROR, "the jump wasn't in a line or the end point had a creature on it");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,-1,1));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Colony is not connected, try again");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,0,3));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "The destination had a creature there and the one you were moving isn't a kamikaze");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,-1,2));//blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,-2,2));//blue

    }

    @Test
    void gameWon() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        gameManager.placeCreature(CRAB, 1, 1);//blue
        gameManager.placeCreature(GRASSHOPPER, 2, 0); //red
        gameManager.placeCreature(GRASSHOPPER, 0, 2);//blue
        gameManager.placeCreature(DOVE, 3, 0);//red
        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, 0, 1)); //blue
        gameManager.placeCreature(GRASSHOPPER, 2, -1); //red
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Butterfly not placed, try placing the butterfly");

        assertEquals(response, gameManager.moveCreature(DOVE, 3, 0,1,0));//red
        assertEquals(response, gameManager.placeCreature(DOVE, 5, 0));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(BUTTERFLY, 3, -1)); //red
        gameManager.placeCreature(CRAB, -1, 1); //blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(DOVE, 3, 0,1,0));//red

        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,0,-1));//blue
        gameManager.placeCreature(GRASSHOPPER, 2, -2); //red
        gameManager.placeCreature(DOVE, 0, 2); //blue
        gameManager.moveCreature(GRASSHOPPER, 2, -2,1,-1);//red

        response = new MoveResponse(MoveResult.RED_WON, "Red won");
        assertEquals(response, gameManager.placeCreature(DOVE, -1, 0)); //blue)

    }

    @Test
    void draggability() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(CRAB, 1, 1));//blue
        assertEquals(response,gameManager.placeCreature(HORSE, 2, 0)); //red
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        assertEquals(response,gameManager.placeCreature(DOVE, 3, 0));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, 0, 1)); //blue
        gameManager.placeCreature(GRASSHOPPER, 3, -1); //red
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response,gameManager.placeCreature(BUTTERFLY, 3, -2)); //red
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, -1, 1));//blue
        assertEquals(response,gameManager.placeCreature(GRASSHOPPER, 2, -2)); //red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        gameManager.moveCreature(GRASSHOPPER, -1, 1,-1,2);//blue


        MoveResponse response2 = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response,gameManager.moveCreature(DOVE, 3, 0,2,1));//red
        gameManager.moveCreature(GRASSHOPPER, -1, 2,-1,1);//blue
        assertEquals(response,gameManager.moveCreature(HORSE, 2, 0,1,-1));//red
        assertEquals(response,gameManager.moveCreature(GRASSHOPPER, -1, 1,-1,2));//blue
        assertEquals(response,gameManager.placeCreature(DOVE, 1, -2)); //red
        assertEquals(response,gameManager.moveCreature(GRASSHOPPER, -1, 2,-1,1));//blue
        response2 = new MoveResponse(MoveResult.MOVE_ERROR, "BFS2 returned null, probably draggability no work");
        assertEquals(response2,gameManager.moveCreature(HORSE, 1, -1,0,-1));//red


    }


    @Test
    void kamikazeTest() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(CRAB, 1, 1));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 2, 0)); //red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        assertEquals(response, gameManager.placeCreature(DOVE, 3, 0));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 1)); //blue
        gameManager.placeCreature(GRASSHOPPER, 3, -1); //red
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 3, -2)); //red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 1));//blue
        assertEquals(response, gameManager.moveCreature(DOVE, 3, 0, 0, 0)); //red
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Butterfly not placed, try placing the butterfly");
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 2, -2)); //blue
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,0,3));//blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, -2, 1));//blue
    }

    @Test
    void RedWinsTest() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");


        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 1, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 1));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 2, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 1));//blue
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 2, 0));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 0));//blue
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 1, -2));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Creature can't reach location");
        assertEquals(response, gameManager.moveCreature(BUTTERFLY, 2, 0,3,-2));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(BUTTERFLY, 2, 0,1,0));//red
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,-1,2));//blue
        response = new MoveResponse(MoveResult.RED_WON, "Red won");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 1, -2,0,-1));//red

    }

    @Test
    void BlueWinsTest() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");


        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 1, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 1));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 2, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 1));//blue
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 2, 0));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 0));//blue
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 1, -2));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.moveCreature(BUTTERFLY, 2, 0,1,0));//red
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, -1, 0,2,0));//blue
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 1, -2,2,-2));//red
        response = new MoveResponse(MoveResult.BLUE_WON, "Blue won");
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, -1, 1,1,1));//blue

    }

    @Test
    void TestForPlacement() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");


        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 1, 0));//blue
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Can't place creature");
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 1, -1));//red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 2, -1));//red
    }

    @Test
    void kamikazeTestJumping() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(CRAB, 1, 1));//blue
        assertEquals(response, gameManager.placeCreature(HORSE, 2, 0)); //red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        assertEquals(response, gameManager.placeCreature(HUMMINGBIRD, 3, 0));//red
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 1)); //blue
        gameManager.placeCreature(GRASSHOPPER, 3, -1); //red
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 0, 0));//blue
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, 3, -2)); //red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, -1, 1));//blue
        assertEquals(response, gameManager.moveCreature(HUMMINGBIRD, 3, 0, 0, 0)); //red
        response = new MoveResponse(MoveResult.MOVE_ERROR, "Butterfly not placed, try placing the butterfly");
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 2, -2)); //blue
        assertEquals(response, gameManager.moveCreature(GRASSHOPPER, 0, 2,0,3));//blue
        response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(BUTTERFLY, -2, 1));//blue
    }

    @Test
    void kamikazeTestWalking() throws IOException { //remake the tests
        String hgcFile = "testConfigurations/Submission1.hgc";
        IHexAround1 gameManager =
                HexAroundGameBuilder.buildGameManager(
                        "testConfigurations/Submission1.hgc");

        MoveResponse response = new MoveResponse(MoveResult.OK, "Legal move");
        assertEquals(response, gameManager.placeCreature(CRAB, 1, 1));//blue
        assertEquals(response, gameManager.placeCreature(DUCK, 2, 0)); //red
        assertEquals(response, gameManager.placeCreature(GRASSHOPPER, 0, 2));//blue
        assertEquals(response, gameManager.moveCreature(DUCK, 2, 0, 1, 0));//blue
    }

}
