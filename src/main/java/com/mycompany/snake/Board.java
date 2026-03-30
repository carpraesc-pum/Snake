package com.mycompany.snake;

import static com.mycompany.snake.SquareType.BODY;
import static com.mycompany.snake.SquareType.FOOD;
import static com.mycompany.snake.SquareType.HEAD;
import static com.mycompany.snake.SquareType.SPECIALFOOD;
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
public class Board extends JPanel implements DrawSquareInterface {

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
    private MyKeyAdapter keyAdapter;
    private Snake snake;
    private DrawSquareInterface drawSquareInterface;
    private Timer timer;
    public static final int DELTA_TIME = 300;
    private Food food;
    private SpecialFood specialFood;
    private SquareType squareType;

    public Board() {
        snake = new Snake(this);
        initBoard();
        initComponents();
        
    }

    private void initBoard() {
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        setFocusable(true);
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent ae) {
                tick();

            }
        });
        initGame();
    }

    private void initGame() {
        timer.start();
    }
    

    private void tick() {
        if (snake.canMove()) {
            snake.move();
        } else {
            //
        }
        repaint();
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
                return new Color(53, 90, 53);
                
            case FOOD:
                return new Color(204, 102, 102);
                
            case SPECIALFOOD:
                return new Color( 255, 215, 0);
                
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
