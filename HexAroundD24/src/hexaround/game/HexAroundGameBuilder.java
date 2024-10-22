/*
 * ******************************************************************************
 *  This files was developed for CS4233: Object-Oriented Analysis & Design.
 *  The course was taken at Worcester Polytechnic Institute.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  * Copyright ©2016-2017 Gary F. Pollice
 *  ******************************************************************************
 *
 * This class:
 * MUST be MODIFIED to create an instance of the IHexAroundGameManager interface.
 * MAY NOT be MOVED from this package.
 */

package hexaround.game;

import hexaround.config.*;
import hexaround.entity.Board; //added this
import hexaround.entity.Creature; //added
import hexaround.entity.CreatureName;
import hexaround.entity.Player; //added

import java.io.*;
import java.util.Collection; //added
import java.util.HashSet;
import java.util.Set;//added

public class HexAroundGameBuilder {
    public static IHexAround1 buildGameManager(String configurationFile) throws IOException {
        HexAroundConfigurationMaker configurationMaker =
            new HexAroundConfigurationMaker(configurationFile);
        GameConfiguration configuration = configurationMaker.makeConfiguration();
        HexAroundFirstSubmission gameManager = new HexAroundFirstSubmission();    // an empty game manager


        //added here
        Set<Player> setPlayers = new HashSet<>();
        for (PlayerConfiguration player:configuration.players()) {
            Player addPlayer = new Player(player.Player(),player.creatures()); //adding a player
                for (CreatureDefinition creature:configuration.creatures()) {

                    //going through the creature definitions that are needed for this one
                    Creature newCreature = new Creature(creature.name(), addPlayer, creature.maxDistance(), creature.properties());
                        if (addPlayer.getAllCreatures().containsKey(newCreature.getName())) {
                            CreatureName nammm = newCreature.getName();
                            for (int i =0; i < addPlayer.getAllCreatures().get(newCreature.getName());i++) //to add the multiples
                            {
                                addPlayer.addCreature(new Creature(creature.name(), addPlayer, creature.maxDistance(), creature.properties()));
                            }

                        }
                }
            setPlayers.add(addPlayer);
        }

        Board board = new Board(setPlayers);

        gameManager.setBoard(board);


        // TODO: Use the configuration to build your game manager
        //myHexAroundGame game = new myHexAroundGame();
        // Make the code readable and use helper methods as needed.
        // Add setters and getters to the game manager that the builder calls.
        //Set<Player> players =
        //Board board = new Board();
        //gameManager.setBoard(board);



        return gameManager;
    }
}
