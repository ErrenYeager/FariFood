package ir.ac.kntu.people;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;

public class Admin {
    private String username;
    private String password;

    public Admin() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void getAndSetUsername(DataStore dataStore) {
        while (true) {
            System.out.print("Enter username: ");
            String username = MyScanner.getInstance().next();
            if (!dataStore.getUsernames().contains(username)) {
                setUsername(username);
                break;
            } else {
                System.out.println("This username has used before.Choose a different one.");
            }
        }
    }

    public void getAndSetPassword(DataStore dataStore) {
        while (true) {
            System.out.print("Enter password: ");
            String password = MyScanner.getInstance().next();
            if (!dataStore.getPasswords().contains(password)) {
                setPassword(password);
                break;
            } else {
                System.out.println("This password has used before.Choose a different one.");
            }
        }
    }

    public void defineUser(DataStore dataStore) {
        System.out.println("Fill following parts:");
        System.out.println("Username: ");
        getAndSetUsername(dataStore);
        System.out.println("Password: ");
        getAndSetPassword(dataStore);
        System.out.println("Successfully defined!");
    }

    public void editUser(DataStore dataStore) {
        System.out.println("Which one do you want to edit?");
        System.out.println("1.Username: " + username + "\n2.Password: " + password);
        int option;
        while (true) {
            System.out.print("Your choice: ");
            option = MyScanner.getInstance().nextInt();
            if(option == 1) {
                System.out.print("New username: ");
                getAndSetUsername(dataStore);
                break;
            } else if(option == 2) {
                System.out.println("New password: ");
                getAndSetPassword(dataStore);
                break;
            } else {
                System.out.println("Invalid input.Try again.");
            }
        }
    }
}
