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
public class SpecialFood extends Food {
    
    
    
    public SpecialFood(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(snake, drawSquareInterface);
        
    }
    
    public void paintFood(Graphics g) {
        drawSquareInterface.drawSquare(g, getRow(), getCol(), SquareType.SPECIALFOOD);
    }
    
    
}
