/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;

/**
 *
 * @author carpraesc
 */
public class Food extends Node {
    public DrawSquareInterface drawSquareInterface;
    
    public Food(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(0, 0); 
        this.drawSquareInterface = drawSquareInterface;
        do {
        int row = (int)(Math.random() * Board.NUM_COLSROWS);
        int col = (int)(Math.random() * Board.NUM_COLSROWS);
        setRow(row);
        setCol(col);
        
        } while (snake.colition(this));
    }
    
    public void paintFood(Graphics g) {
        drawSquareInterface.drawSquare(g, getRow(), getCol(), SquareType.FOOD);
    }
}
