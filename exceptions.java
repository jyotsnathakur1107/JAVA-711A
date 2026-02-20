import java.util.InputMismatchException;
import java.util.Scanner;
public class Marks {
    public static void main (String args[]) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter marks : ");
            int marks = sc.nextInt();

            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100");
            }

            String grade;
            if (marks >= 90) grade = "A";
            else if (marks >= 75) grade = "B";
            else if (marks >= 60) grade = "C";
            else if (marks >= 40) grade = "D";
            else grade = "Fail";

            System.out.println(name);
            System.out.println(marks);
            System.out.println(grade);
        }

        catch (InputMismatchException ex) {
            System.out.println("Please enter numeric values only");
        }

        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        finally {
            System.out.println("Marks evaluation completed");
        }
    }
}