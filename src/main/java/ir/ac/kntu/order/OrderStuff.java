package ir.ac.kntu.order;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Rating;
import ir.ac.kntu.helperclasses.Time;
import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.markets.SuperMarket;
import ir.ac.kntu.people.Customer;

import java.util.ArrayList;

public class OrderStuff extends OrderCommodity {
    private Time[] orderInterval = new Time[2];
    private Rating rateToCommodity;
    private int amount;

    public void setOrderInterval(Time[] orderInterval) {
        this.orderInterval = orderInterval;
    }

    public void scanAndSetAmount(int max) {
        int amount;
        while (true) {
            System.out.println("There is " + max + " from this commodity in Supermarket");
            System.out.print("Set amount: ");
            amount = MyScanner.getInstance().nextInt();
            if(amount > 0 && amount <= max) {
                this.amount = amount;
                break;
            } else {
                System.out.println("Invalid choice!Try again.");
                System.out.println("There is not enough from this commodity now. Reduce amount of your order");
            }
        }
    }

    @Override
    public void orderMenu(DataStore dataStore) {
        System.out.println("Select the customer you want to order commodity for:");
        Customer customer = showCustomersToSet(dataStore.getCustomers());
        System.out.println("Enter time you want the commodity: ");
        Time orderTime = new Time();
        orderTime.scanAndSetTime();
        ArrayList<DepartmentStore> openFruitShops = getOpenDepartments(dataStore.getSuperMarkets(), orderTime);
        System.out.println("Select the method you want for searching:");
        searchingMenu(openFruitShops);
        orderCommodity(customer, dataStore);
    }

    @Override
    public void orderCommodity(Customer customer, DataStore dataStore) {
        SuperMarket superMarket = (SuperMarket) getChosenDepartmentStoreForOrdering();
        superMarket.priceNameOfCommoditiesAndPrice();
        int choice = checkOption(superMarket.getMenu().size());
        --choice;
        scanAndSetAmount(superMarket.getMenu().get(choice).getCount());
        setOrderInterval(superMarket.showAndChooseDeliveryTimes());
        if(superMarket.getDeliveries() != null) {
            superMarket.getMenu().get(choice).setCount(superMarket.getMenu().get(choice).getCount() - amount);
            super.orderCommodity(customer, dataStore);
            setRateForCommodity(choice, superMarket);
            customer.addToOrders(this);
            dataStore.addOrderCommodity(this);
        } else {
            System.out.println("There is no delivery.Sorry try later :(");
        }
    }

    public void setRateForCommodity(int index, SuperMarket chosenSuperMarket) {
        System.out.println(chosenSuperMarket.getMenu().get(index).getName());
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
        chosenSuperMarket.getMenu().get(index).rateTheCommodity(rating.getEnum(rate));
        setRateToCommodity(rating.getEnum(rate));
    }

    public void setRateToCommodity(Rating rateToCommodity) {
        this.rateToCommodity = rateToCommodity;
    }

}
