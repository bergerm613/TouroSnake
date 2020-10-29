package touro.snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;

    public GardenView(Garden garden) {
        this.garden = garden;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrass(g);
        paintConsideredSquares(g);
        paintProjectedPath(g);
        paintFood(g);
        paintSnake(g);
    }

    void paintGrass(Graphics g) {
        // Berger
        g.setColor(new Color(153, 194,77));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(new Color(46, 64,87));
        paintListOfSquares(garden.getSnake().getSquares(), g);
    }

    void paintFood(Graphics g) {
        // Berger
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(new Color(241, 143,1));
            paintSquare(food, g);
        }
    }

    void paintConsideredSquares(Graphics g) {
        g.setColor(new Color(199, 215, 185));
        paintListOfSquares(garden.getSnake().getStrategy().getSearchSpace(), g);
    }

    void paintProjectedPath(Graphics g) {
        g.setColor(new Color(4, 139,168));
        paintListOfSquares(garden.getSnake().getStrategy().getPath(), g);
    }

    private void paintListOfSquares(List<Square> squareList, Graphics g) {
        for (Square square : squareList) {
            paintSquare(square, g);
        }
    }

    private void paintSquare(Square square, Graphics g) {
        int x = square.getX() * CELL_SIZE;
        int y = square.getY() * CELL_SIZE;
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }
}
