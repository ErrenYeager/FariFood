package ir.ac.kntu.markets;

import ir.ac.kntu.helperclasses.*;
import ir.ac.kntu.people.Delivery;
import ir.ac.kntu.stuff.Fruit;

import java.util.ArrayList;

public class FruitShop extends DepartmentStore {
    private ArrayList<Fruit> menu;
    private final ArrayList<Time> deliveryTimes = new ArrayList<>();
    private int maxOfWeightToDeliver;

    public FruitShop() {
    }

    public int getMaxOfWeightToDeliver() {
        return maxOfWeightToDeliver;
    }

    @Override
    public void defineDepartmentStore(ArrayList<Delivery> definedDeliveries, ArrayList<Address> definedAddresses) {
        super.defineDepartmentStore(definedDeliveries, definedAddresses);
        scanAndSetMenu();
        setDeliveryTimes();
        scanAndSetMaxOfWeightToDeliver();
    }

    private void setDeliveryTimes() {
        int hour = getWorkTime()[1].getHour() - getWorkTime()[0].getHour();
        if(hour < 0) {
            hour += 24;
        }
        int hourToSet = getWorkTime()[0].getHour();
        for (int i = 0; i < (hour/2) + 1; i++) {
            Time time = new Time(hourToSet,0);
            deliveryTimes.add(time);
            hourToSet += 2;
        }
    }

    public void scanAndSetMaxOfWeightToDeliver() {
        System.out.print("Set max of weight to deliver: (Max choice possible = 10)");
        while (true) {
            int max = MyScanner.getInstance().nextInt();
            if(max <= 10 && max > 0) {
                maxOfWeightToDeliver = max;
                break;
            } else {
                System.out.println("Out of range.Try again.");
            }
        }
    }

    public void setMenu(ArrayList<Fruit> menu) {
        this.menu = menu;
    }

    @Override
    public void editDepartment(ArrayList<Address> definedAddresses) {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("Choose");
            System.out.println("1.Edit menu");

            System.out.println("3.Edit Others");
            System.out.println("4.Exit");
            int choice = checkOption(3);
            switch (choice) {
                case 1:
                    editMenu();
                    break;
                case 2:
                    editAmountOfCommodities();
                    break;
                case 3:
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
            Fruit newFruit = new Fruit();
            newFruit.defineCommodity();
            menu.add(newFruit);
        }
    }

    public ArrayList<Fruit> getMenu() {
        return menu;
    }

    @Override
    public void priceNameOfCommoditiesAndPrice() {
        for(int i = 0; i < menu.size(); i++) {
            System.out.println(i+1 + "." + menu.get(i).getName() + " : " + menu.get(i).getPrice() + " Count: " + menu.get(i).getCount());
        }
    }

    private void scanAndSetMenu() {
        System.out.println("\nAdd fruit to your menu");
        ArrayList<Fruit> fruits = new ArrayList<>();
        while (true) {
            Fruit newDefinedFruit = new Fruit();
            newDefinedFruit.defineCommodity();
            fruits.add(newDefinedFruit);
            System.out.println("\nDo you want to define another fruit to your menu? (Y/N)");
            String anotherOrNot = MyScanner.getInstance().next();
            if(!(anotherOrNot.matches("\\s*[Yy]\\s*"))) {
                break;
            }
        }
        setMenu(fruits);
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
}