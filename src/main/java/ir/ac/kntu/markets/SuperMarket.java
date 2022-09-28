package ir.ac.kntu.markets;

import ir.ac.kntu.helperclasses.Address;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Time;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.people.Delivery;
import ir.ac.kntu.stuff.Stuff;

import javax.management.MBeanRegistration;
import java.util.ArrayList;

public class SuperMarket extends DepartmentStore {
    private ArrayList<Stuff> menu;
    private ArrayList<Time> deliveryTimes = new ArrayList<>();
    private int[] countOfPossibleOrderInIntervals;
    private ArrayList<Customer> specialCustomers = new ArrayList<>();

    public SuperMarket() {
    }

    @Override
    public void defineDepartmentStore(ArrayList<Delivery> definedDeliveries, ArrayList<Address> definedAddresses) {
        super.defineDepartmentStore(definedDeliveries, definedAddresses);
        scanAndSetMenu();
        setDeliveryTimes();
        setCountOfPossibleOrderInIntervals();
    }

    public void setCountOfPossibleOrderInIntervals() {
        System.out.println("Set count of possible orders in each interval");
        countOfPossibleOrderInIntervals = new int[deliveryTimes.size() - 1];
        int count;
        for(int i = 0; i < countOfPossibleOrderInIntervals.length; i++) {
            while (true) {
                System.out.print("Count of (" + deliveryTimes.get(i).toString() + ") - (" + deliveryTimes.get(i+1).toString() + ") : ");
                count = MyScanner.getInstance().nextInt();
                if(count > 0) {
                    countOfPossibleOrderInIntervals[i] = count;
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }
    }

    public ArrayList<Stuff> getMenu() {
        return menu;
    }

    private void setDeliveryTimes() {
        int hour = getWorkTime()[1].getHour() - getWorkTime()[0].getHour();
        if(hour < 0) {
            hour += 24;
        }
        int hourToSet = getWorkTime()[0].getHour();
        for (int i = 0; i < hour+1; i++) {
            Time time = new Time(hourToSet,0);
            deliveryTimes.add(time);
            hourToSet++;
        }
    }



    public void setMenu(ArrayList<Stuff> menu) {
        this.menu = menu;
    }

    private void scanAndSetMenu() {
        System.out.println("\nAdd stuffs to your menu");
        ArrayList<Stuff> stuffs = new ArrayList<>();
        while (true) {
            Stuff newDefinedStuff = new Stuff();
            newDefinedStuff.defineCommodity();
            stuffs.add(newDefinedStuff);
            System.out.println("\nDo you want to define another stuffs to your menu? (Y/N)");
            String anotherOrNot = MyScanner.getInstance().next();
            if(!(anotherOrNot.matches("\\s*[Yy]\\s*"))) {
                break;
            }
        }
        setMenu(stuffs);
    }

    @Override
    public void editDepartment(ArrayList<Address> definedAddresses) {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("Choose");
            System.out.println("1.Edit menu");
            System.out.println("2.Edit count of possible order in intervals");
            System.out.println("3.Edit special customers");
            System.out.println("4.Edit amount of commodities");
            System.out.println("5.Edit Others");
            System.out.println("6.Exit");
            int choice = checkOption(4);
            switch (choice) {
                case 1:
                    editMenu();
                    break;
                case 2:
                    setCountOfPossibleOrderInIntervals();
                    break;
                case 3:
                    editSpecialCustomers();
                    break;
                case 4:
                    editAmountOfCommodities();
                case 5:
                    super.editDepartment(definedAddresses);
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void editAmountOfCommodities() {
        for(int i = 0,j = 1; i < menu.size(); i++, j++) {
            System.out.println(j + ". " + menu.get(i).getName() + " - " + menu.get(i).getCount());
        }
        int choice = checkOption(menu.size());
        --choice;
        menu.get(choice).scanAndSetCount();
        System.out.println("Successfully edited!");
    }

    public void editSpecialCustomers() {
        System.out.println("** Edit Special Customers Menu: **");
        System.out.println("1.Remove a special customer");
        System.out.println("2.Exit");
        int choice = checkOption(2);
        if(choice == 1) {
            for(int i = 0, j = 1; i < specialCustomers.size(); i++, j++) {
                System.out.println(j + ". " + specialCustomers.get(i).getUsername() + " - " + specialCustomers.get(i).getPhoneNumber());
            }
            System.out.println("Choose which one do oyu want to remove");
            int whichCustomer = checkOption(specialCustomers.size());
            --whichCustomer;
            specialCustomers.remove(whichCustomer);
            System.out.println("Removed successfully!");
        } else {
            System.out.println("Back to previews menu...");
        }
    }

    public void editMenu() {
        System.out.print("Remove or Add?\n1.Remove\n2.Add\n3.Exit\nYour choice: ");
        int option = checkOption(3);
        if(option == 1) {
            priceNameOfCommoditiesAndPrice();
            System.out.print("\nWhich one do you want to remove? ");
            int choice = checkOption(menu.size());
            --choice;
            menu.remove(choice);
        } else if(option == 2) {
            System.out.print("Define new food");
            Stuff newStuff = new Stuff();
            newStuff.defineCommodity();
            menu.add(newStuff);
        }
    }

    public Time[] showAndChooseDeliveryTimes() {
        Time[] interval = new Time[2];
        for(int i = 1, j = 0; i < deliveryTimes.size(); i++,j++) {
            System.out.println(i + ". " + deliveryTimes.get(j).getHour() + " - " + deliveryTimes.get(i).getHour());
        }
        int choice = checkOption(deliveryTimes.size() - 3);
        --choice;
        interval[0] = deliveryTimes.get(choice);
        interval[1] = deliveryTimes.get(++choice);
        return interval;
    }

    public void addSpecialCustomer(Customer customer) {
        specialCustomers.add(customer);
    }
}