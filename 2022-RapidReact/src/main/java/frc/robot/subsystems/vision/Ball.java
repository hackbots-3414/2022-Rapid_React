package frc.robot.subsystems.vision;

public class Ball {

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    Ball(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static String toString(Ball ball) {
        return "Pos: (" + Integer.toString(ball.x) + ", " + Integer.toString(ball.y) + "); Size: (" + Integer.toString(ball.width) + ", " + Integer.toString(ball.height) + ")";
    }
}