package frc.robot;

public class Util {
    public static double reduceToOne(double input) {
        if (input >= 1.0) {
            return input;
        }
        else if (input < 0) {
            return 0.0;
        }
        else {
            return 1.0;
        }
    }
}