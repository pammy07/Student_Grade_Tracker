import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentGradeManager {

    
    private Map<String, List<Double>> gradesMap;

    public StudentGradeManager() {
        gradesMap = new HashMap<>();
    }

    
    public void addGrade(String subject, double grade) {
        gradesMap.putIfAbsent(subject, new ArrayList<>());
        gradesMap.get(subject).add(grade);
    }

    
    public double calculateAverageGrade() {
        double total = 0;
        int count = 0;
        for (List<Double> grades : gradesMap.values()) {
            for (Double grade : grades) {
                total += grade;
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    
    public double calculateGPA() {
        double average = calculateAverageGrade();
        return average / 10.0;  
    }

    
    public String getLetterGrade() {
        double average = calculateAverageGrade();
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }

    
    public void displaySummary() {
        System.out.println("Grades Summary:");
        for (Map.Entry<String, List<Double>> entry : gradesMap.entrySet()) {
            String subject = entry.getKey();
            List<Double> grades = entry.getValue();
            System.out.printf("Subject: %s\n", subject);
            for (Double grade : grades) {
                System.out.printf(" - Grade: %.2f\n", grade);
            }
        }

        double average = calculateAverageGrade();
        double gpa = calculateGPA();
        String letterGrade = getLetterGrade();

        System.out.printf("\nOverall Average Grade: %.2f\n", average);
        System.out.printf("GPA: %.2f\n", gpa);
        System.out.printf("Letter Grade: %s\n", letterGrade);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentGradeManager manager = new StudentGradeManager();
        boolean running = true;

        while (running) {
            System.out.println("\nStudent Grade Manager");
            System.out.println("1. Add Grade");
            System.out.println("2. Display Summary");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();  
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter grade (0.0 - 100.0): ");
                    double grade;
                    try {
                        grade = scanner.nextDouble();
                        scanner.nextLine();  
                        if (grade < 0 || grade > 100) {
                            throw new IllegalArgumentException();
                        }
                        manager.addGrade(subject, grade);
                        System.out.println("Grade added successfully.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid grade. Please enter a numeric value.");
                        scanner.next();  
                    } catch (IllegalArgumentException e) {
                        System.out.println("Grade must be between 0.0 and 100.0.");
                    }
                    break;

                case 2:
                    manager.displaySummary();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    break;
            }
        }

        scanner.close();
    }
}
