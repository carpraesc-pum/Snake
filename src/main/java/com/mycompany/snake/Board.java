package com.mycompany.snake;
 
import com.mycompany.snake.interfaces.DrawSquareInterface;
import com.mycompany.snake.interfaces.Incrementer;
import com.mycompany.snake.interfaces.GameOverInterface;
import com.mycompany.snake.interfaces.InitGamer;
import java.awt.Color;
import java.awt.Font;
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
 
    public static int numColsRows = 20;
    public static final int MIN_SPECIAL_TIME = 1000;
    public static final int MAX_SPECIAL_TIME = 3000;
    public static int deltaTimer = 300;
    private DrawSquareInterface drawSquareInterface;
    private SquareType squareType;
    private MyKeyAdapter keyAdapter;
    private Snake snake;
    private Timer timer;
    private Timer specialTimer;
    private Timer timeTrialTimer;
    private Food food;
    private SpecialFood specialFood;
    private Incrementer incrementer;
    private GameOverInterface gameOverInterface;
    private int timeTrial;
    private int counterTimeTrial;
    private boolean timeTrialMode = false;  // el jugador decide si activarlo desde el menú
 
    // ── Setters para el menú ─────────────────────────────────────────────────
 
    /**
     * Activa o desactiva el contrarreloj.
     * Llámalo desde Game.java con el valor del JCheckBox del menú.
     * Ejemplo: board.setTimeTrialMode(jCheckBoxTimeTrial.isSelected());
     */
    public void setTimeTrialMode(boolean timeTrialMode) {
        this.timeTrialMode = timeTrialMode;
    }
 
    /**
     * Fija cuántas foods aparecen en el tablero a la vez.
     * Llámalo desde Game.java con el valor del JSpinner del menú.
     * Ejemplo: board.setNumFoods((int) jSpinnerFoods.getValue());
     */
    
 
    // ─────────────────────────────────────────────────────────────────────────
 
    public Board() {
        snake = new Snake(this);
        initBoard();
        initComponents();
    }
 
    private void initBoard() {
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        setFocusable(true);
        timeTrial = 10;
        counterTimeTrial = 1;
        food = new Food(snake,this);
        specialFood = null;
 
        int specialtime = (int) (Math.random() * (MAX_SPECIAL_TIME - MIN_SPECIAL_TIME)) + MIN_SPECIAL_TIME;
        specialTimer = new Timer(specialtime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                specialFood = new SpecialFood(snake, Board.this);
            }
        });
 
        timer = new Timer(deltaTimer, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
 
        timeTrialTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startTimeTrial(true);
                isTimeTrialEnd();
            }
        });
    }
 
    public void initGame() {
        if (incrementer != null) {
            incrementer.resetScore();
        }
        timeTrial = 10;
        snake = new Snake(this);
        specialFood = null;
 
 
        timer.start();
        specialTimer.start();
        if (timeTrialMode) {           
            timeTrialTimer.start();
        }
    }
 
    public void clear() {
        snake = null;
        food = null;
        specialFood = null;
        repaint();
    }
 
    private void tick() {
        if (snake.canMove()) {
            snake.move();
                if (snake.eatFood(food)) {
                snake.grow(1);
                food = new Food(snake, this);
                incrementer.incrementScore(1);
                incrementer.incrementScore(1);
                timeTrial += 4;  
            }
            if (snake.eatFood(specialFood)) {
                snake.grow(3);
                specialFood = new SpecialFood(snake, this);
                incrementer.incrementScore(3);
                timeTrial += 8;        
            }
        } else {
            doGameOver();
        }
        repaint();
    }
 
    public void startTimeTrial(boolean beggin) {
        if (beggin) {
            timeTrial -= counterTimeTrial;
        }
    }
 
    public boolean isTimeTrialEnd() {
        if (timeTrial <= 0) {
            doGameOver();
            return true;
        }
        return false;
    }
    
    public void turnOnTimeTrial(boolean b) {
        this.timeTrialMode = b;
    }
 
    public void changeSpeedSnake(int numSpeed) {
        deltaTimer = numSpeed;
        timer.setDelay(numSpeed);
    }
 
    public void changeNumBoard(int numBoxes) {
        numColsRows = numBoxes;
    }
 
    public void setGameOver(GameOverInterface gameOverInterface) {
        this.gameOverInterface = gameOverInterface;
    }
 
    public void doGameOver() {
        timer.stop();
        specialTimer.stop();
        timeTrialTimer.stop();
        gameOverInterface.setVisible(this);
    }
 
    public void pause() {
        if (timer.isRunning()) {
            timer.stop();
            specialTimer.stop();
            if (timeTrialMode) {
                timeTrialTimer.stop();
            }
        } else {
            timer.start();
            specialTimer.start();
            if (timeTrialMode) {
                timeTrialTimer.start();
            }
        }
    }
 
    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (snake != null) {
            snake.paint(g);
        }
        /*if (food != null) {
            for (Food f : food) {
                f.paintFood(g);
            }
        }*/
        food.paintFood(g);
        if (specialFood != null) {
            specialFood.paintFood(g);
        }
        if (timeTrialMode) {           // el contador solo se pinta si el modo está activo
            paintTimeTrial(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }
 
    private void paintTimeTrial(Graphics g) {
        Color color = timeTrial <= 3 ? new Color(204, 102, 102)
                                     : new Color(37, 211, 102);
        g.setColor(color);
        g.setFont(new Font("Open Sans Semibold", Font.BOLD, 18));
        g.drawString("Time: " + timeTrial, squareWidth() * numColsRows - 80, 20);
    }
 
    private int squareWidth() {
        return getWidth() / numColsRows;
    }
 
    private int squareHeight() {
        return getHeight() / numColsRows;
    }
 
    public void drawSquare(Graphics g, int row, int col, SquareType type) {
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = getSquareColor(type);
 
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
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
            case HEAD:        return new Color(37, 211, 102);
            case BODY:        return new Color(77, 251, 142);
            case FOOD:        return new Color(204, 102, 102);
            case SPECIALFOOD: return new Color(255, 202, 102);
            default: throw new AssertionError();
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
