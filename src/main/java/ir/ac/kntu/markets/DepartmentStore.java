package ir.ac.kntu.markets;

import ir.ac.kntu.helperclasses.*;
import ir.ac.kntu.people.Delivery;

import java.util.*;

public class DepartmentStore {
    private String name;
    private Address address;
    private Time[] workTime;
    private DepartmentType departmentType;
    private ArrayList<Rating> ratings= new ArrayList<>();
    private double averageOfRatings;
    private ArrayList<Delivery> deliveries = new ArrayList<>();

    public DepartmentStore() {
    }

    public void rateDepartmentStore(Rating rating) {
        ratings.add(rating);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Time[] getWorkTime() {
        Time[] copyOfWorkTime = workTime;
        return copyOfWorkTime;
    }

    public void setWorkTime(Time[] workTime) {
        this.workTime = workTime;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentStoreType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public ArrayList<Rating> getRatings() {
        ArrayList<Rating> copyOfRatings = new ArrayList<>();
        Collections.copy(copyOfRatings,ratings);
        return copyOfRatings;
    }

    public ArrayList<Delivery> getDeliveries() {
        ArrayList<Delivery> copyOfDeliveries = new ArrayList<>(deliveries);
        return copyOfDeliveries;
    }

    public void addDelivery(Delivery delivery) {
        delivery.updateNumberOfRestaurantWorking();
        deliveries.add(delivery);
    }

    public double getAverageOfRatings() {
        if(ratings.size() < 1) {
            return 0;
        }
        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getValue();
        }
        averageOfRatings = (float)sum/ratings.size();
        return averageOfRatings;
    }

    public void priceNameOfCommoditiesAndPrice() {
        System.out.println(name + ": " + "\n");

    }

    public void defineDepartmentStore(ArrayList<Delivery> definedDeliveries, ArrayList<Address> definedAddresses) {
        System.out.println("Give following information to define a department:");
        System.out.print("Name of department : ");
        String name = MyScanner.getInstance().next();
        Address address = new Address();
        address.defineAddress(definedAddresses);
        System.out.println("Hours of work :");
        Time startTime = new Time();
        Time endTime = new Time();
        System.out.println("From :");
        startTime.scanAndSetTime();
        System.out.println("Until :");
        endTime.scanAndSetTime();
        Time[] workTime = {startTime, endTime};
        setFields(name,address,workTime);
        handleDepartmentType();
        handleDeliveryOfDepartment(definedDeliveries);

    }

    private void setFields(String name, Address address, Time[] workTime) {
        setWorkTime(workTime);
        setAddress(address);
        setName(name);
    }

    public void handleDeliveryOfDepartment(ArrayList<Delivery> definedDeliveries) {
        System.out.println("Choose one of the following option to define a delivery for your restaurant:");
        System.out.println("1. Define a new delivery\n2. Choose from defined deliveries in advance");
        System.out.print("Your choice : ");
        int choice;
        while (true) {
            choice = MyScanner.getInstance().nextInt();
            if(choice == 1) {
                Delivery newDelivery = new Delivery();
                newDelivery.defineDelivery();
                deliveries.add(newDelivery);
                addDelivery(newDelivery);
                break;
            } else if(choice == 2) {
                this.showAndChooseDeliveries(definedDeliveries);
                break;
            } else {
                System.out.println("Invalid choice. Choose again");
            }
        }
    }

    public void showAndChooseDeliveries(ArrayList<Delivery> definedDeliveries) {
        for(int i = 0; i < definedDeliveries.size(); i++) {
            System.out.println(i+1 + ". " + definedDeliveries.get(i).toString());
        }
        if(definedDeliveries.size() < 1) {
            System.out.println("There is nothing to show.Define new delivery.");
        } else {
            System.out.println("Choose one of the options above.");
        }
        System.out.println("0. Back");
        int choice;
        while (true) {
            System.out.print("Your choice: ");
            choice = MyScanner.getInstance().nextInt();
            --choice;
            if(choice == -1) {
                handleDeliveryOfDepartment(definedDeliveries);
            }
            if(choice >= 0 && choice < definedDeliveries.size()) {
                if(definedDeliveries.get(choice).getNumberOfRestaurantWorking() > 2) {
                    System.out.println("This delivery is already working for 2 different restaurants.Pick another one.");
                } else {
                    this.addDelivery(definedDeliveries.get(choice));
                    definedDeliveries.get(choice).updateNumberOfRestaurantWorking();
                    System.out.println("Successfully added");
                    break;
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Do you want to add another one? (Y/N)");
        String anotherOrNot = MyScanner.getInstance().next();
        if(anotherOrNot.matches("\\s*[Yy]\\s*")) {
            handleDeliveryOfDepartment(definedDeliveries);
        }
    }

    public void handleDepartmentType() {
        System.out.println("Select department type :");
        System.out.println("1.ECONOMIC\n2.NORMAL\n3.LUXURY");
        int choice = checkOption(3);
        if (choice == 1) {
            setDepartmentStoreType(DepartmentType.ECONOMIC);
        } else if (choice == 2) {
            setDepartmentStoreType(DepartmentType.NORMAL);
        } else if (choice == 3) {
            setDepartmentStoreType(DepartmentType.LUXURY);
        }
    }

    public void editName() {
        System.out.print("New name: ");
        String name = MyScanner.getInstance().next();
        setName(name);
        System.out.println("Successfully edited!");
    }

    public void editAddress(ArrayList<Address> definedAddresses) {
        System.out.print("New Address: ");
        Address newAddress = new Address();
        newAddress.defineAddress(definedAddresses);
        setAddress(newAddress);
        System.out.println("Successfully edited!");
    }

    public int checkOption(int maxPossibleChoice) {
        int userInput;
        while (true) {
            System.out.print("Your choice: ");
            userInput = MyScanner.getInstance().nextInt();
            if (userInput > 0 && userInput < maxPossibleChoice) {
                return userInput;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public void editWorkTime() {
        System.out.print("New work time");
        Time start = new Time();
        Time end = new Time();
        start.scanAndSetTime();
        end.scanAndSetTime();
        Time[] newWorkTime = {start, end};
        setWorkTime(newWorkTime);
    }

    public void editDelivery() {
        System.out.print("Choose one of the followings:\n1.Define new delivery and add\n2.Use defined ones\nYour choice: ");
        int optionOfDelivery;
        while (true) {
            optionOfDelivery = MyScanner.getInstance().nextInt();
            if(optionOfDelivery == 1) {
                Delivery newDelivery = new Delivery();
                newDelivery.defineDelivery();
                addDelivery(newDelivery);
                break;
            } else {
                System.out.println("\n" + deliveries.toString());
                int numberOfDelivery;
                while (true) {
                    System.out.print("\nPick one to add: ");
                    numberOfDelivery = MyScanner.getInstance().nextInt();
                    if(numberOfDelivery <= deliveries.size()) {
                        addDelivery(deliveries.get(numberOfDelivery));
                        break;
                    } else {
                        System.out.println("Try again.Invalid choice.");
                    }
                }
            }
        }
        System.out.println("Done!");
    }

    public Address getAddress() {
        return address;
    }

    public void editDepartment(ArrayList<Address> definedAddresses) {
        int choice;
        boolean shouldBreak = false;
        while (true) {
            System.out.println("Choose one of the options below:");
            System.out.println("1.Name");
            System.out.println("2.Address");
            System.out.println("3.Work Time");
            System.out.println("4.Restaurant type");
            System.out.println("5.Deliveries");
            System.out.println("6.Exit");
            System.out.print("Which one do you want to edit?\nYour choice: ");
            choice = MyScanner.getInstance().nextInt();
            switch (choice) {
                case 1:
                    editName();
                    break;
                case 2:
                    editAddress(definedAddresses);
                    break;
                case 3:
                    editWorkTime();
                    break;
                case 4:
                    System.out.print("New restaurant type");
                    handleDepartmentType();
                    System.out.println("Successfully edited!");
                    break;
                case 5:
                    editDelivery();
                    break;
                case 6:
                    shouldBreak = true;
                    break;
                default:
                    break;
            }
            if(shouldBreak) {
                System.out.println("Back to restaurants menu...");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Restaurant { " +
                "Name: '" + name + '\'' +
                ", Address: " + address +
                ", WorkTime :" + Arrays.toString(workTime) +
                ", RestaurantType :" + departmentType +
                ", AverageOfRatings: " + getAverageOfRatings() +
                ", Deliveries :" + deliveries +
                " }";
    }
}
