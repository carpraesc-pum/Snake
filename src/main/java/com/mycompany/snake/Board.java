package com.mycompany.snake;

import com.mycompany.snake.interfaces.Incrementer;
import static com.mycompany.snake.SquareType.BODY;
import static com.mycompany.snake.SquareType.FOOD;
import static com.mycompany.snake.SquareType.HEAD;
import static com.mycompany.snake.SquareType.SPECIALFOOD;
import com.mycompany.snake.interfaces.GameOverInterface;
import com.mycompany.snake.interfaces.InitGamer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author carpraesc
 */
public class Board extends JPanel implements DrawSquareInterface, InitGamer {

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGHT) {
                        snake.changeDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.changeDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.changeDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.changeDirection(Direction.DOWN);
                    }
                    break;
            }
        }
    }
    // para que el timer solo se repita una vez hay que utilizar el setRepeats(false); 
    public static final int NUM_COLSROWS = 20;
    public static final int MIN_SPECIAL_TIME = 1000;
    public static final int MAX_SPECIAL_TIME = 5000;
    public static final int DELTA_TIME = 200;
    
    private MyKeyAdapter keyAdapter;
    private Snake snake;
    private DrawSquareInterface drawSquareInterface;
    private Timer timer;
    private Timer specialTimer; 
    private Food food;
    private SpecialFood specialFood;
    private SquareType squareType;
    private Incrementer incrementer;
    private GameOverInterface gameOverInterface;

    public Board() {
        snake = new Snake(this);
        initBoard();
        initComponents();
        
    }

    private void initBoard() {
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        setFocusable(true);
        food = new Food(snake, this);
        specialFood = null;
        int specialtime = (int) (Math.random() * (MAX_SPECIAL_TIME - MIN_SPECIAL_TIME)) + MIN_SPECIAL_TIME;
        specialTimer = new Timer(specialtime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //specialFood = new SpecialFood(this);
                specialFood = new SpecialFood(snake, Board.this);
            }
        });
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent ae) {
                tick();

            }
        });
        initGame();
    }

    public void initGame() {
        if (incrementer != null) {
            incrementer.resetScore();
        }
        timer.start();
        specialTimer.start();
        snake = new Snake(this);
    }
    

    private void tick() {
        if (snake.canMove()) {
            snake.move();
            if (snake.eatFood(food)) {
                snake.grow(1);
                food = new Food(snake, this);
                incrementer.incrementScore(1);
                
            }
            if (snake.eatFood(specialFood)) {
                snake.grow(3);
                specialFood = new SpecialFood(snake, this);
                incrementer.incrementScore(3);

            }
        } else {
            doGameOver();
        }
        repaint();
    }
    
    public void setGameOver(GameOverInterface gameOverInterface) {
        this.gameOverInterface = gameOverInterface;
    }
    
    public void doGameOver() {
        timer.stop();
        specialTimer.stop();
        gameOverInterface.setVisible(this);
    }
    
    public void pause() {
        if (timer.isRunning()) {
            timer.stop();
            specialTimer.stop();
        } else {
            timer.start();
            specialTimer.start();
        }
    }
    
    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    private void paintBorderBoard(Graphics g) {
        g.setColor(Color.black);
        int width = squareWidth() * NUM_COLSROWS;
        int height = squareHeight() * NUM_COLSROWS;
        g.drawRect(0, 0, width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.paint(g);
        food.paintFood(g);
        if (specialFood != null) {
            specialFood.paintFood(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private int squareWidth() {
        return getWidth() / NUM_COLSROWS;
    }

    private int squareHeight() {
        return getHeight() / NUM_COLSROWS;
    }

    public void drawSquare(Graphics g, int row, int col,
            SquareType type) {
        /*Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };*/
        int x = col * squareWidth();
        int y = row * squareHeight();
        //Color color = isHead ? new Color(204, 102, 102) : new Color(102, 102, 204);
        Color color = getSquareColor(type);
        
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
        
    }
    
    private Color getSquareColor(SquareType type) {
        switch (type) {
            case HEAD:
                return new Color(102, 102, 204);
                
            case BODY:
                return new Color(150, 150, 200);
                
            case FOOD:
                return new Color(204, 102, 102);
                
            case SPECIALFOOD:
                return new Color( 255, 202, 102);
                
            default:
                throw new AssertionError();
        }
               
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
