import java.awt.*;
import javax.swing.*;

public class Circle extends JPanel {
    private Coordinate coordinate;
    private Color color;
    private int radius;

    private final int WIDTH = 1280, HEIGHT = 720;

    public Circle(Coordinate coordinate, Color color, int radius) {
        this.coordinate = coordinate;
        this.color = color;
        this.radius = radius;
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.color);
        g2d.fillOval((int) this.coordinate.getX(), (int) this.coordinate.getY(), this.radius, this.radius);
    }
}
