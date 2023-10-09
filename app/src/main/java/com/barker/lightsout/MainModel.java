package com.barker.lightsout;

import java.util.Random;

/**
 * MainModel
 * Stores and manipulates the game's data
 */
public class MainModel {
    private boolean[][] states;
    private final Random random;
    private boolean solved;
    private int squares;

    /**
     * Constructor for the model
     */
    public MainModel() {
        random = new Random();
        setSquares(5);
    }

    /**
     * getSquares
     * gets the side count
     * @return the squares value
     */
    public int getSquares() {
        return squares;
    }

    /**
     * setSquares
     * sets the size of the board and resets its data
     * @param squares the number of squares per side
     */
    public void setSquares(int squares) {
        this.squares = squares;
        states = new boolean[squares][squares];
        randomizeStates();
    }

    /**
     * randomizeStates
     * Randomizes the state of each tile, effectively resetting the game
     */
    public void randomizeStates() {
        boolean state = random.nextBoolean();
        for (int i = 0; i < squares; i++) {
            for (int j = 0; j < squares; j++) {
                states[i][j] = state;
            }
        }

        int clicks = (int) Math.pow(squares, 2) / 2;
        for (int i = 0; i < clicks; i++) {
            pressState(random.nextInt(squares-1), random.nextInt(squares-1));
        }

        checkSolved();
    }

    /**
     * pressState
     * Manipulates the states for a click action
     *
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
     *
     * @param row the row to check
     * @param col the column to check
     *
     * @return the state at that position
     */
    public boolean getState(int row, int col) {
        if (row < 0 || col < 0 || row >= squares || col >= squares) {
            return false;
        }

        return states[row][col];
    }

    /**
     * getSolved
     * gets whether the board is solved
     *
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
     *
     * @param row row to affect
     * @param col column to affect
     *
     * @return whether the row and column were in bounds
     */
    private boolean pressStateInternal(int row, int col) {
        if (row < 0 || col < 0 || row >= squares || col >= squares) {
            return false;
        }

        states[row][col] = !states[row][col];
        return true;
    }

    /** PRIVATE
     * checkStates
     * Checks if the game is all dark or all light
     *
     * @return if the game is all dark/all light
     */
    private boolean checkStates() {
        for (int i = 0; i < squares; i++) {
            for (int j = 0; j < squares; j++) {
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
     *
     * @return whether the lights are all off
     */
    private boolean checkStatesInverse() {
        for (int i = 0; i < squares; i++) {
            for (int j = 0; j < squares; j++) {
                if (states[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}