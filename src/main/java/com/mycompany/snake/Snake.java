/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.interfaces.DrawSquareInterface;
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
        int middle = Board.numColsRows / 2;
        for (int i = 0; i < 4; i++) {
            Node node = new Node(middle, middle + i - 4);
            addNode(node);
        }
        nodesToGrow = 0;

    }
    
    public void grow(int amount) {
        nodesToGrow += amount;
    }
    
    
    
    public boolean eatFood(Food food) {
        if (food == null) {
            return false;
        }
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        return (food.getRow() == row && food.getCol() == col);
    }
   
    
    public Direction getDirection() {
        return direction;
    }
    
    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }
    
    public boolean contains(Node node) {
        for (Node n : nodes) {
            if (node.getCol() == n.getCol() && node.getRow() == n.getRow()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canMove() {
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        Node node = null;
        switch (direction) {
            case UP:
                node = new Node(row - 1, col);
                break;
            case DOWN:
                node = new Node(row + 1, col);
                break;
            case LEFT:
                node = new Node(row, col - 1);
                break;
            case RIGHT:
                node = new Node(row, col + 1);
                break;
        }
        if (node.getRow() < 0 || node.getRow() >= Board.numColsRows ||
                node.getCol() < 0 || node.getCol() >= Board.numColsRows || colidesWithItself(node)) {
            return false;
        }
        
        return true;
    }
    
    public boolean colidesWithItself(Node nodeX) {
        for (Node node: nodes) {
            if (nodeX.getRow() == node.getRow() && nodeX.getCol() == node.getCol()) {
                return true;
            }
        }
        return false;
    }
    
    

    public void move() {
        Node node = null;
        switch (direction) {
            case UP:
                node = new Node(nodes.getFirst().getRow() - 1, nodes.getFirst().getCol());
                break;
            case DOWN:
                node = new Node(nodes.getFirst().getRow() + 1, nodes.getFirst().getCol());
                break;
            case LEFT:
                node = new Node(nodes.getFirst().getRow(), nodes.getFirst().getCol() - 1);
                break;
            case RIGHT:
                node = new Node(nodes.getFirst().getRow(), nodes.getFirst().getCol() + 1);
                break;
        }
        nodes.addFirst(node);
        if (nodesToGrow > 0) {
            nodesToGrow--;
        } else {
            nodes.remove(nodes.getLast());
        }
    }

    public void addNode(Node node) {
        nodes.add(0, node);
    }

    public void paint(Graphics g) {
        boolean first = true;
        for (Node node : nodes) {
            if (first) {
                first = false;
                drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), SquareType.HEAD);
            } else {
                drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), SquareType.BODY);
            } 
        }

    }
    /*
        - falata el menu del game Over
        - Bug: cuando das dos teclas muy rapido detecta como hit
        

        - tres en raya o hundir la flota
    */
}
