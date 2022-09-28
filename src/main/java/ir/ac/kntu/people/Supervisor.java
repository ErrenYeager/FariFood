package ir.ac.kntu.people;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.markets.FruitShop;
import ir.ac.kntu.markets.Restaurant;
import ir.ac.kntu.markets.SuperMarket;

public class Supervisor extends Admin {
    private String phoneNumber;
    private DepartmentStore workingDepartmentStore;

    public Supervisor() {
    }

    public void showInfo() {
        System.out.println("** INFORMATION **");
        System.out.println("1. Username: " + super.getUsername());
        System.out.println("2. Password: " + super.getPassword());
        System.out.println("3. Phone number: " + phoneNumber);
        System.out.println("4. Working department: " + workingDepartmentStore.getName());
    }

    @Override
    public void editUser(DataStore dataStore) {
        showInfo();
        System.out.println("Which one do you want to edit?");
        System.out.println("\n1.Phone number: " + phoneNumber + "2.Username and password");
        int option;
        while (true) {
            System.out.print("Your choice: ");
            option = MyScanner.getInstance().nextInt();
            if(option == 1) {
                System.out.print("New phone number: ");
                String newPhoneNumber = MyScanner.getInstance().next();
                setPhoneNumber(newPhoneNumber);
                break;
            } else if(option == 2) {
                super.editUser(dataStore);
            } else {
                System.out.println("Invalid input.Try again.");
            }
        }

    }

    @Override
    public void defineUser(DataStore dataStore) {
        super.defineUser(dataStore);
        System.out.print("Phone number: ");
        String phoneNumber = MyScanner.getInstance().next();
        setPhoneNumber(phoneNumber);
        setWorkingDepartmentStore(dataStore);
    }

    private void showDepartmentStore(DataStore dataStore) {
        int number;
        for (int i = 0; i < dataStore.getDepartmentStores().size(); i++) {
            number = i+1;
            System.out.println(number + ". " + dataStore.getSpecificDepartment(i));
        }
    }

    public void setWorkingDepartmentStore(DataStore dataStore) {
        System.out.println("Set the department you are working for: ");
        System.out.println("1.Submit your department");
        System.out.println("2.Use defined ones");
        int choice = checkOption(2);
        if(choice == 1) {
            chooseWhichDepartment(dataStore);
        } else {
            showDepartmentStore(dataStore);
            int option = checkOption(dataStore.getDepartmentStores().size() - 1);
            workingDepartmentStore = dataStore.getSpecificDepartment(option);
        }
    }

    public void chooseWhichDepartment(DataStore dataStore) {
        System.out.println("\n1.Restaurant");
        System.out.println("2.FruitShop");
        System.out.println("3.SuperMarket");
        int choice = checkOption(3);
        if(choice == 1) {
            Restaurant restaurant = new Restaurant();
            restaurant.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
            workingDepartmentStore = restaurant;
        } else  if(choice == 2) {
            FruitShop fruitShop = new FruitShop();
            fruitShop.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
            workingDepartmentStore = fruitShop;
        } else {
            SuperMarket superMarket = new SuperMarket();
            superMarket.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
            workingDepartmentStore = superMarket;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.matches("\\s*09\\d{9}\\s*")) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.print("Invalid phone number!Common Mistakes:\n" +
                    "1.First two digits must be 09\n2.phone number should be 11 digits\n3.No space between digits\n");
            System.out.print("Phone number: ");
            phoneNumber = MyScanner.getInstance().next();
            setPhoneNumber(phoneNumber);
        }
    }

    public DepartmentStore getWorkingDepartmentStore() {
        return workingDepartmentStore;
    }

    public int checkOption(int maxPossibleChoice) {
        int userInput;
        while (true) {
            System.out.print("Your choice: ");
            userInput = MyScanner.getInstance().nextInt();
            if (userInput > 0 && userInput <= maxPossibleChoice) {
                return userInput;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    @Override
    public String toString() {
        return "SuperVisor { " +
                "PhoneNumber : '" + phoneNumber + '\'' +
                ", WorkingDepartmentStore : " + workingDepartmentStore.getName() +
                " }";
    }
}