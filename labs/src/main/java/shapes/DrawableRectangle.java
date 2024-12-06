package shapes;

import geometry.Vec2d;

import java.awt.*;

public class DrawableRectangle extends Rectangle implements Drawable {
    final private Color color;

    public DrawableRectangle(Vec2d p, double width, double height, Color color) {
        super(p, width, height);
        this.color = color;
    }

    public void draw(Graphics2D g) {
        // todo: implement
        // draw a filled rectangle with width and height centred at position
        int x = (int) (getPosition().x() - width / 2);
        int y = (int) (getPosition().y() - height / 2);
        g.setColor(color);
        g.fillRect(x, y, (int) width, (int) height);
    }
}
