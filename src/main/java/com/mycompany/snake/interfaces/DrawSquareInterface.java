/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.snake.interfaces;

import com.mycompany.snake.SquareType;
import java.awt.Graphics;

/**
 *
 * @author carpraesc
 */
public interface DrawSquareInterface {
    public void drawSquare(Graphics g, int row, int col, SquareType type);
}
