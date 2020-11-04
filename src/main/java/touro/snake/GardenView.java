package touro.snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;

    public static final Color darkBlueColor = new Color(46, 64, 87);
    public static final Color orangeColor = new Color(241, 143, 1);
    public static final Color lightGreenColor = new Color(199, 215, 185);
    public static final Color lightBlueColor = new Color(4, 139, 168);
    public static final Color darkGreenColor = new Color(153, 194, 77);

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
        g.setColor(darkGreenColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(darkBlueColor);
        paintListOfSquares(garden.getSnake().getSquares(), g);
    }

    void paintFood(Graphics g) {
        // Berger
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(orangeColor);
            paintSquare(food, g);
        }
    }

    void paintConsideredSquares(Graphics g) {
        g.setColor(lightGreenColor);
        paintListOfSquares(garden.getSnake().getStrategy().getSearchSpace(), g);
    }

    void paintProjectedPath(Graphics g) {
        g.setColor(lightBlueColor);
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
