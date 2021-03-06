package com.bulumutka.polyconstr.ui;

import com.bulumutka.polyconstr.models.MathHelper;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Edge2D<Vertex extends Vertex2D> implements Drawable {
    public Vertex source;
    public Vertex target;
    public String time;

    public Edge2D(Vertex source, Vertex target, String time) {
        this.source = source;
        this.target = target;
        this.time = time;
    }

    @Override
    public void draw(Canvas canvas) {
        double x1 = source.getX();
        double y1 = source.getY();
        double x2 = target.getX();
        double y2 = target.getY();
        var g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.setStroke(Color.BLACK);
        g.strokeLine(x1, y1, x2, y2);
        g.strokeLine(x1, y1, x2, y2);
        g.fillText(time, (x1 + x2) / 2 + 10, (y1 + y2) / 2 + 10);
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    private List<Double> cutLine(double x1, double y1, double x2, double y2, double value) {
        var s1 = x2 - x1;
        var s2 = y2 - y1;
        var dist = MathHelper.dist(x1, y1, x2, y2);
        var n1 = s1 / Math.sqrt(dist);
        var n2 = s2 / Math.sqrt(dist);
        var s3 = n1 * (dist - value);
        var s4 = n2 * (dist - value);
        return Arrays.asList(x1, y1, x1 + s3, y1 + s4);
    }
}
