package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableCircle extends Circle implements Drawable {
    private Color color;

    public DrawableCircle(Vec2d p, double radius, Color color) {
        super(p, radius);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        // todo: implement
        // draw a filled circle centre at position with radius
        int diameter = (int) (2 * radius);
        int x = (int) (getPosition().x() - radius);
        int y = (int) (getPosition().y() - radius);
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
}
