import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


public class Schedule {
    private static final String FILE_NAME = "schedule.txt";
    public static void main(String[] args) {
        List<Event> schedule = loadScheduleFromFile();
        Scanner scnr = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
            LocalDate today = LocalDate.now();
            System.out.println("Today's date: " + today);
            displayMenu();
            System.out.print("-->: ");
            choice = scnr.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Loading this week's schedule");
                    printSchedule(schedule);
                    break;
                case 2:
                    System.out.println("Editing schedule");
                    editSchedule(schedule, scnr);
                    break;
                case 3:
                    System.out.println("Saving schedule");
                    saveScheduleToFile(schedule);
                    System.out.println("Goodbye");
                    scnr.close(); // Closing scanner here
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    System.out.println("Goodbye");
                    System.exit(0);
            }
        }
        scnr.close(); // Close the scanner when the loop ends
    }
    
    public static void displayMenu() {
        System.out.println("1. View this week's schedule");
        System.out.println("2. Edit Schedule");
        System.out.println("3. Exit");
    }

    public static void printSchedule(List<Event> schedule) {
        //print schedule for the week in order by date and provide day of the week
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(7);
        for (LocalDate date = today; date.isBefore(endOfWeek); date = date.plusDays(1)) {
            System.out.println(date.getDayOfWeek() + " " + date);
            for (Event e : schedule) {
                if (e.getDueDate().equals(date)) {
                    System.out.println(e.getName() + ": " + e.getDescription());
                }
            }
            System.out.println();
        }
    }

    public static void editSchedule(List<Event> schedule, Scanner scnr) {
        System.out.println("1. Add an event");
        System.out.println("2. Remove an event");
        System.out.println("3. Return to main menu");
        System.out.print("-->: ");
        int choice = scnr.nextInt();
        switch (choice) {
            case 1:
                addEvent(schedule, scnr);
                break;
            case 2:
                removeEvent(schedule, scnr);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid choice");
                System.out.println("Goodbye");
                System.exit(0);
        }
    }

    public static void addEvent(List<Event> schedule, Scanner scnr) {
        System.out.println("Enter the name of the event: ");
        scnr.nextLine(); // Consume the newline character
        String name = scnr.nextLine();
        System.out.println("Enter the date of the event (YYYY-MM-DD): ");
        String date = scnr.nextLine();
        System.out.println("Enter the description of the event: ");
        String description = scnr.nextLine();
        Event newEvent = new Event(name, LocalDate.parse(date), description);
        schedule.add(newEvent);
        System.out.println("Event added\n");
    }

    public static void removeEvent(List<Event> schedule, Scanner scnr) {
        System.out.println("Enter the name of the event to remove: ");
        scnr.nextLine(); // Consume the newline character
        String name = scnr.nextLine();
        boolean removed = false;
        for (Event e : schedule) {
            if (e.getName().equals(name)) {
                schedule.remove(e);
                removed = true;
                break;
            }
        }
        if (removed) {
            System.out.println("Event removed");
        } else {
            System.out.println("Event not found");
        }
    }


    public static List<Event> loadScheduleFromFile() {
        List<Event> schedule = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    LocalDate dueDate = LocalDate.parse(parts[1]);
                    String description = parts[2];
                    Event event = new Event(name, dueDate, description);
                    schedule.add(event);
                } else {
                    System.out.println("Invalid format for event: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public static void saveScheduleToFile(List<Event> schedule) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Event event : schedule) {
                writer.write(event.getName() + "," + event.getDueDate() + "," + event.getDescription());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
