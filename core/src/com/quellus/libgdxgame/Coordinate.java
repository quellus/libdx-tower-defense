package com.quellus.libgdxgame;

public class Coordinate<Type> {
    private Type x;
    private Type y;

    public Coordinate(Type x, Type y) {
        this.x = x;
        this.y = y;
    }

    public Type getX() {
        return x;
    }

    public void setX(Type x) {
        this.x = x;
    }

    public Type getY() {
        return y;
    }

    public void setY(Type y) {
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
