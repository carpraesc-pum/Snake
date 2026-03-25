/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGHT;
import static com.mycompany.snake.Direction.UP;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carpraesc
 */
public class Snake {
    private List<Node> nodes;
    private Direction direction;
    private DrawSquareInterface drawSquareInterface;
    private int nodesToGrow;
   
    
    public Snake(DrawSquareInterface drawSquareInterface) {
        nodes = new ArrayList<Node>();
        direction = Direction.RIGHT;
        this.drawSquareInterface = drawSquareInterface;
        int middle = Board.NUM_COLSROWS / 2;
        for (int i = 0; i < 4; i++) {
            Node node = new Node(middle, middle + i);
            addNode(node);
        }
        nodesToGrow = 0;
        
    }
    
    public boolean canMove() {
        switch (direction) {
            case UP:
                if (nodes.getFirst().getRow() - 1 < 0) {
                    return false;
                }
                break;
            case DOWN:
                if (nodes.getFirst().getRow() + 1 >= Board.NUM_COLSROWS) {
                    return false;
                }
                break;
            case LEFT:
                if (nodes.getFirst().getCol() - 1 < 0) {
                    return false;
                }
                break;
            case RIGHT:
                if (nodes.getFirst().getCol() + 1 >= Board.NUM_COLSROWS) {
                    return false;
                }
                break;
        }
        return true;
    }
                
     
    
    public void move() {
           switch (direction) {
            case UP:
                nodes.getFirst().getRow() + 1;
                break;
            case DOWN:
                
                break;
            case LEFT:
                
                break;
            case RIGHT:
                
                break;
        }
    }
    
    public void addNode(Node node) {
        nodes.add(0, node);
    }
    
    public void paint(Graphics g) {
        boolean first = true;
        for(Node node : nodes) {
            drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), first);
            if (first) {
                first = false;
            }
        }
        
    }
    
    
    
    
}
