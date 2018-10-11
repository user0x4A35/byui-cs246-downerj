import java.util.Scanner;

public class CircleCalculator {
    public double getRadius() {
        double radius = 0.0;
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("What is the radius? ");
        radius = scanner.nextDouble();
        scanner.close();
        
        return radius;
    }

    public void displayCircumference(double radius) {
        double circumference = Math.PI * (2 * radius);
        System.out.printf("The circumference is %.2f%n", circumference);
    }

    public void displayArea(double radius) {
        double area = Math.PI * (radius * radius);
        System.out.printf("The area is %.2f%n", area);
    }

    public static void main(String[] args) {
        CircleCalculator cal = new CircleCalculator();
        double radius = cal.getRadius();
        cal.displayCircumference(radius);
        cal.displayArea(radius);
    }
}