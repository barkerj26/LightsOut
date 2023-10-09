package com.barker.lightsout;

import java.util.Random;

/**
 * MainModel
 * Stores and manipulates the game's data
 */
public class MainModel {
    private final boolean[][] states;
    private final Random random;
    private boolean solved;

    /**
     * Constructor for the model
     */
    public MainModel() {
        states = new boolean[5][5];
        random = new Random();
        solved = false;
        randomizeStates();
    }

    /**
     * randomizeStates
     * Randomizes the state of each tile, effectively resetting the game
     */
    public void randomizeStates() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                states[i][j] = random.nextBoolean();
            }
        }
        checkSolved();
    }

    /**
     * pressState
     * Manipulates the states for a click action
     * @param row row to click on
     * @param col column to click on
     */
    public void pressState(int row, int col) {
        if (!pressStateInternal(row, col)) {
            return;
        }
        pressStateInternal(row - 1, col);
        pressStateInternal(row + 1, col);
        pressStateInternal(row, col - 1);
        pressStateInternal(row, col + 1);
    }

    /**
     * getState
     * returns the state at a position
     * @param row the row to check
     * @param col the column to check
     * @return the state at that position
     */
    public boolean getState(int row, int col) {
        if (row < 0 || col < 0 || row > 4 || col > 4) {
            return false;
        }

        return states[row][col];
    }

    /**
     * getSolved
     * gets whether the board is solved
     * @return true if the board is solved and false otherwise
     */
    public boolean getSolved() {
        return solved;
    }

    /**
     * checkSolved
     * updates the model's solved value appropriately
     */
    public void checkSolved() {
        solved = checkStates();
    }

    /** PRIVATE
     * pressStateInternal
     * The internal, repeated code for pressState
     * @param row row to affect
     * @param col column to affect
     * @return whether the row and column were in bounds
     */
    private boolean pressStateInternal(int row, int col) {
        if (row < 0 || col < 0 || row > 4 || col > 4) {
            return false;
        }

        states[row][col] = !states[row][col];
        return true;
    }

    /** PRIVATE
     * checkStates
     * Checks if the game is all dark or all light
     * @return if the game is all dark/all light
     */
    private boolean checkStates() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!states[i][j]) {
                    return checkStatesInverse();
                }
            }
        }

        return true;
    }

    /** PRIVATE
     * checkStatesInverse
     * the 'second half' of checkStates
     * @return whether the lights are all off
     */
    private boolean checkStatesInverse() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (states[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}