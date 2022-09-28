package ir.ac.kntu.people;

import ir.ac.kntu.helperclasses.Address;
import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.order.OrderCommodity;

import java.util.ArrayList;

public class Customer extends Admin {
    private String phoneNumber;
    private Address address;
    private ArrayList<OrderCommodity> orderedInAdvance = new ArrayList<>();

    public Customer() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.matches("\\s*0{1}9{1}\\d{9}\\s*")) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.print("Invalid phone number!Common Mistakes:\n" +
                             "1.First two digits must be 09\n2.phone number should be 11 digits\n3.No space between digits\n");
            System.out.print("Phone number: ");
            phoneNumber = MyScanner.getInstance().next();
            setPhoneNumber(phoneNumber);
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public void defineUser(DataStore dataStore) {
        super.defineUser(dataStore);
        System.out.println("Fill following parts to define a customer:");
        System.out.print("Phone number: ");
        String customerPhoneNumber = MyScanner.getInstance().next();
        setPhoneNumber(customerPhoneNumber);
        System.out.print("Address : ");
        Address customerAddress = new Address();
        setAddress(customerAddress);
        customerAddress.defineAddress();
        System.out.println("Successfully defined!");
    }

    @Override
    public void editUser(DataStore dataStore) {
        System.out.println("Which one do you want to edit?");
        showInfo();
        System.out.println("\n1.Phone number: " + phoneNumber + "\n2.Address: " + address + "3.Username and password");
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
                System.out.println("New address: ");
                Address newAddress = new Address();
                newAddress.defineAddress();
                setAddress(newAddress);
                break;
            } else if(option == 3) {
                super.editUser(dataStore);
            } else {
                System.out.println("Invalid input.Try again.");
            }
        }
    }

    public void showInfo() {
        System.out.println("** INFORMATION **");
        System.out.println("1. Username: " + super.getUsername());
        System.out.println("2. Password: " + super.getPassword());
        System.out.println("3. Phone number: " + phoneNumber);
        System.out.println("4. Address: " + address.toString());
    }

    public void addToOrders(OrderCommodity orderedFood) {
        orderedInAdvance.add(orderedFood);
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
        return "Customer{" +
                "phoneNumber = '" + phoneNumber + "'\n" +
                ", address = " + address +
                '}';
    }
}
