package ir.ac.kntu.markets;

import ir.ac.kntu.helperclasses.Address;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Time;
import ir.ac.kntu.people.Delivery;
import ir.ac.kntu.stuff.Food;
import java.util.ArrayList;

public class Restaurant extends DepartmentStore {
    private ArrayList<Food> menu;
    private ArrayList<Time> deliveryTimes = new ArrayList<>();

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Food> menu) {
        this.menu = menu;
    }

    private void scanAndSetMenu() {
        System.out.println("\nAdd foods to your menu");
        ArrayList<Food> foods = new ArrayList<>();
        while (true) {
            Food newDefinedFood = new Food();
            newDefinedFood.defineCommodity();
            foods.add(newDefinedFood);
            System.out.println("\nDo you want to define another food to your menu? (Y/N)");
            String anotherOrNot = MyScanner.getInstance().next();
            if(!(anotherOrNot.matches("\\s*[Yy]\\s*"))) {
                break;
            }
        }
        setMenu(foods);
    }

    @Override
    public void priceNameOfCommoditiesAndPrice() {
        super.priceNameOfCommoditiesAndPrice();
        for(int i = 0,j = 1; i < menu.size(); i++,j++) {
            System.out.println(j + ". " + menu.get(i).getName() + " : " + menu.get(i).getPrice());
        }
    }

    @Override
    public void defineDepartmentStore(ArrayList<Delivery> definedDeliveries, ArrayList<Address> definedAddresses) {
        super.defineDepartmentStore(definedDeliveries, definedAddresses);
        scanAndSetMenu();
    }

    @Override
    public void editDepartment(ArrayList<Address> definedAddresses) {
        System.out.println("Choose");
        System.out.println("1.Edit menu");
        System.out.println("2.Edit Others");
        int choice = checkOption(2);
        if(choice == 1) {
            editMenu();
        } else {
            super.editDepartment(definedAddresses);
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
            Food newFood = new Food();
            newFood.defineCommodity();
            menu.add(newFood);
        }
    }
}
