package shapes;


import geometry.Vec2d;

import java.util.ArrayList;

public class Polygon extends MovableShape {
    ArrayList<Vec2d> vertices;

    public Polygon(Vec2d p, ArrayList<Vec2d> vertices) {
        super(p);
        this.vertices = vertices;
    }

    public double area() {
        // todo: implement using the shoelace formula
        double area = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d current = vertices.get(i);
            Vec2d next = vertices.get((i + 1) % n);
            area += current.x() * next.y() - current.y() * next.x();
        }
        return Math.abs(area) / 2.0;
    }

    public double perimeter() {
        // todo: implement by summing the lengths of the edges
        double perimeter = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vec2d current = vertices.get(i);
            Vec2d next = vertices.get((i + 1) % n);
            perimeter += current.distance(next);
        }
        return perimeter;
    }
}
