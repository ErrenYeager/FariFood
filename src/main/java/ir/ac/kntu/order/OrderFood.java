package ir.ac.kntu.order;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Rating;
import ir.ac.kntu.helperclasses.Time;
import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.markets.Restaurant;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.stuff.Commodity;
import ir.ac.kntu.stuff.Fruit;

import java.util.ArrayList;

public class OrderFood extends OrderCommodity {
    private Rating rateToCommodity;
    
    @Override
    public void orderMenu(DataStore dataStore) {
        System.out.println("Select the customer you want to order commodity for:");
        Customer customer = showCustomersToSet(dataStore.getCustomers());
        System.out.println("Enter time you want the commodity: ");
        Time orderTime = new Time();
        orderTime.scanAndSetTime();
        ArrayList<DepartmentStore> openFruitShops = getOpenDepartments(dataStore.getRestaurants(), orderTime);
        System.out.println("Select the method you want for searching:");
        searchingMenu(openFruitShops);
        orderCommodity(customer, dataStore);
    }

    @Override
    public void orderCommodity(Customer customer, DataStore dataStore) {
        Restaurant restaurant = (Restaurant) getChosenDepartmentStoreForOrdering();
        restaurant.priceNameOfCommoditiesAndPrice();
        int choice;
        while (true) {
            System.out.print("Pick a food to order: ");
            choice = MyScanner.getInstance().nextInt();
            --choice;
            if (choice >= 0 && choice < restaurant.getMenu().size()) {
                setCommodity(restaurant.getMenu().get(choice));
                break;
            } else {
                System.out.println("Invalid choice.Try again.");
            }
        }
        if(restaurant.getDeliveries() != null) {
            super.orderCommodity(customer, dataStore);
            setRateForCommodity(choice,restaurant);
            customer.addToOrders(this);
            dataStore.addOrderCommodity(this);
        } else {
            System.out.println("There is no delivery.Sorry try later :(");
        }
    }

    public void setRateForCommodity(int index, Restaurant chosenFruitShop) {
        System.out.println(chosenFruitShop.getMenu().get(index).getName());
        Rating rating = Rating.AWFUL;
        System.out.println("Rate the commodity(1,2,3,4,5)");
        int rate;
        while (true) {
            System.out.print("Set your rate: ");
            rate = MyScanner.getInstance().nextInt();
            if(rate > 0 && rate < 6) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
        chosenFruitShop.getMenu().get(index).rateTheCommodity(rating.getEnum(rate));
        setRateToCommodity(rating.getEnum(rate));
    }

    public void setRateToCommodity(Rating rateToCommodity) {
        this.rateToCommodity = rateToCommodity;
    }

    @Override
    public void setCommodity(Commodity commodity) {
        Fruit fruit = (Fruit) commodity;
        if(fruit.getCount() > 0) {
            super.setCommodity(null);
        } else {
            super.setCommodity(fruit);
        }
    }
}
