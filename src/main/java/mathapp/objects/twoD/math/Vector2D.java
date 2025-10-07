package mathapp.objects.twoD.math;

import mathapp.objects.twoD.Drawable2D;

import java.awt.*;
import java.awt.geom.Point2D;

public class Vector2D implements Drawable2D {
    double x, y;
    double startX, startY;
    boolean visible;
    Color color;
    String name;


    public Vector2D(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.startX = 0;
        this.startY = 0;
        visible = true;
        color = Color.red;
        this.name = name;
    }

    public Vector2D(Vector2D vector, String name) {
        this.x = vector.x;
        this.y = vector.y;
        this.startX = vector.startX;
        this.startY = vector.startY;
        visible = true;
        color = Color.red;
        this.name = name;
    }

    public Vector2D(double x, double y, double startX, double startY, String name) {
        this.x = x;
        this.y = y;
        this.startX = startX;
        this.startY = startY;
        visible = true;
        color = Color.red;
        this.name = name;
    }


    public double getX() {return x;}
    public double getY() {return y;}
    public boolean isVisible() {return visible;}
    public void setVisible(boolean visible) {this.visible = visible;}
    public String getName() {return name;}
    public Color getColor() {return color;}
    public void setColor(Color color) {this.color = color;}

    public void draw(Graphics2D g2d, int scale, Point2D.Double origin) {
        if(!visible) return;

        g2d.setColor(color);
        double endX = scale*(startX + x);
        double endY = scale*(startY + y);
        g2d.drawLine((int)startX*scale,(int)startY*scale, (int)endX, (int)endY);
        double theta = getAngle();
        g2d.drawLine((int)endX,(int)endY,(int)(endX-20*Math.cos(Math.toRadians(30)+theta)),(int)(endY-20*Math.sin(Math.toRadians(30)+theta)));
        g2d.drawLine((int)endX,(int)endY,(int)(endX-20*Math.cos(theta-Math.toRadians(30))),(int)(endY-20*Math.sin(theta-Math.toRadians(30))));
    }

    public double getAngle() {
        return Math.atan2(y,x);
    }

    public Vector2D addVector(Vector2D vector) {
        return new Vector2D(this.x+vector.x,this.y+vector.y, this.name+vector.name);
    }

}
