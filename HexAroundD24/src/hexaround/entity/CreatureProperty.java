/*
 * Copyright (c) 2023. Gary F. Pollice
 *
 * This files was developed for personal or educational purposes. All rights reserved.
 *
 *  You may use this software for any purpose except as follows:
 *  1) You may not submit this file without modification for any educational assignment
 *      unless it was provided to you as part of starting code that does not require modification.
 *  2) You may not remove this copyright, even if you have modified this file.
 */

package hexaround.entity;

public enum CreatureProperty {
    QUEEN("Butterfly"),
    WALKING("Walking"),
    RUNNING("Running"),
    FLYING("Flying"),
    JUMPING("Jumping"),
    INTRUDING("Intruding"),
    TRAPPING("Trapping"),
    SWAPPING("Swapping"),
    KAMIKAZE("Kamikaze"),
    HATCHING("Hatching");

    private final String name;

    private CreatureProperty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
