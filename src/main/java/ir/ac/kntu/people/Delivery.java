package ir.ac.kntu.people;

import ir.ac.kntu.helperclasses.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Delivery {
    private TypeOfVehicle typeOfVehicle;
    private TypeOfSalary typeOfSalary;
    private int salary;
    private AbsencePresence[] workdays;
    private int numberOfRestaurantWorking = 0;
    private ArrayList<Integer> ratings = new ArrayList<>();
    private double averageOfRating;

    public Delivery(TypeOfVehicle typeOfVehicle, TypeOfSalary typeOfSalary, int salary, AbsencePresence[] workdays, int numberOfRestaurantWorking, ArrayList<Integer> ratings, double averageOfRating) {
        this.typeOfVehicle = typeOfVehicle;
        this.typeOfSalary = typeOfSalary;
        this.salary = salary;
        this.workdays = workdays;
        this.numberOfRestaurantWorking = numberOfRestaurantWorking;
        this.ratings = ratings;
        this.averageOfRating = averageOfRating;
    }

    public Delivery() {
    }

    public void printInfo(int number) {
        String salaryType;
        String[] workingDays = new String[7];
        if(typeOfSalary == TypeOfSalary.ON_ORDER) {
            salaryType = "ON_ORDER";
        } else {
            salaryType = "ON_TIME";
        }
        for(int i = 0; i < 7; i++){
            if(workdays[i] == AbsencePresence.ABSENT) {
                workingDays[i] = "A";
            } else {
                workingDays[i] = "P";
            }
        }
        System.out.println("\nNUMBER (" + number + ")");
        System.out.println("1. Type of vehicle: " + typeOfVehicle);
        System.out.println("2. Type and cost of salary(per hour/per trip): " + salaryType + " - " + salary);
        System.out.print("3. Working days: [ ");
        for(int i = 0; i < workingDays.length; i++) {
            System.out.print(workingDays[i] + " ");
        }
        System.out.print("]\n\n");
    }

    public void setTypeOfVehicle(TypeOfVehicle typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public void setTypeOfSalary(TypeOfSalary typeOfSalary) {
        this.typeOfSalary = typeOfSalary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setWorkdays(AbsencePresence[] workdays) {
        this.workdays = workdays;
    }

    public int getNumberOfRestaurantWorking() {
        return numberOfRestaurantWorking;
    }

    public void updateNumberOfRestaurantWorking() {
        ++numberOfRestaurantWorking;
    }

    public void defineDelivery() {
        System.out.println("\nFill the following information :\n");
        handleVehicleType();
        handleSalaryTypeAndCostOfSalary();
        handleWorkingTime();
    }

    public void handleWorkingTime() {
        AbsencePresence[] schedule = new AbsencePresence[7];
        String[] weekDays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for(int i = 0; i < 7; i++) {
            System.out.println(weekDays[i] + ": ");
            System.out.println("1.Absent\n2.Present");
            System.out.print("Your choice: ");
            schedule[i] = checkAndSetInput();
        }
        this.setWorkdays(schedule);
    }

    public AbsencePresence checkAndSetInput() {
        int option;
        while (true) {
            option = MyScanner.getInstance().nextInt();
            if(option == 1) {
                return AbsencePresence.ABSENT;
            } else if(option == 2) {
                return AbsencePresence.PRESENT;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public void handleVehicleType() {
        System.out.println("Choose vehicle type of delivery :");
        System.out.println("1.Motorbike\n2.Car");
        System.out.print("Your choice: ");
        int choice;
        while(true) {
            choice = MyScanner.getInstance().nextInt();
            if(choice == 1) {
                this.setTypeOfVehicle(TypeOfVehicle.MOTORBIKE);
                break;
            } else if(choice == 2) {
                this.setTypeOfVehicle(TypeOfVehicle.CAR);
                break;
            } else {
                System.out.println("Just 1 or 2.Try again.");
            }
        }
    }

    public void handleSalaryTypeAndCostOfSalary() {
        System.out.println("Choose salary type of delivery :");
        System.out.println("1.Depending on working hours");
        System.out.println("2.Depending on the number of trips");
        int choice;
        while(true) {
            choice = MyScanner.getInstance().nextInt();
            if(choice == 1) {
                this.setTypeOfSalary(TypeOfSalary.ON_TIME);
                System.out.print("Cost of salary on each hour : ");
                int salary;
                while(true) {
                    salary = MyScanner.getInstance().nextInt();
                    if(salary > 0) {
                        this.setSalary(salary);
                        break;
                    } else {
                        System.out.println("Invalid input! Try again.");
                    }
                }
                break;
            } else if(choice == 2) {
                System.out.print("Cost of salary on each trip : ");
                int salary;
                while(true) {
                    salary = MyScanner.getInstance().nextInt();
                    if(salary > 0) {
                        this.setSalary(salary);
                        break;
                    } else {
                        System.out.println("Invalid input! Try again.");
                    }
                }
                break;
            } else {
                System.out.println("Just 1 or 2.Try again.");
            }
        }
    }

    public void editDelivery() {
        int choice;
        while (true) {
            System.out.println("Choose one of the options below:");
            System.out.println("1.Type of vehicle");
            System.out.println("2.Type and cost of salary");
            System.out.println("3.Work days");
            System.out.print("Your choice: ");
            choice = MyScanner.getInstance().nextInt();
            switch (choice) {
                case 1:
                    this.handleVehicleType();
                    break;
                case 2:
                    this.handleSalaryTypeAndCostOfSalary();
                    break;
                case 3:
                    this.handleWorkingTime();
                    break;
                default:
                    System.out.println("Invalid input.Try again.");
                    break;
            }
            System.out.print("Do you want to edit another information?(Y/N)");
            String anotherOrNot = MyScanner.getInstance().next();
            if(!(anotherOrNot.matches("\\s*[Yy]{1}\\s*"))) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Delivery{typeOfVehicle=" + typeOfVehicle +
                ", typeOfSalary=" + typeOfSalary +
                ", salary=" + salary +
                ", workdays=" + Arrays.toString(workdays) +
                ", numberOfRestaurantWorking=" + numberOfRestaurantWorking +
                ", ratings=" + ratings +
                ", averageOfRating=" + averageOfRating +
                '}';
    }
}

