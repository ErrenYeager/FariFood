package ir.ac.kntu.order;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Rating;
import ir.ac.kntu.helperclasses.Time;
import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.markets.FruitShop;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.stuff.Commodity;
import ir.ac.kntu.stuff.Fruit;

import java.util.ArrayList;

public class OrderFruit extends OrderCommodity {
    private Time[] orderInterval = new Time[2];
    private Rating rateToCommodity;
    private int amount;

    public OrderFruit() {
    }

    @Override
    public void orderMenu(DataStore dataStore) {
        System.out.println("Select the customer you want to order commodity for:");
        Customer customer = showCustomersToSet(dataStore.getCustomers());
        System.out.println("Enter time you want the commodity: ");
        Time orderTime = new Time();
        orderTime.scanAndSetTime();
        ArrayList<DepartmentStore> openFruitShops = getOpenDepartments(dataStore.getFruitShops(), orderTime);
        System.out.println("Select the method you want for searching:");
        searchingMenu(openFruitShops);
        orderCommodity(customer, dataStore);
    }

    public void setRateForCommodity(int index, FruitShop chosenFruitShop) {
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

    @Override
    public void orderCommodity(Customer customer, DataStore dataStore) {
        FruitShop fruitShop = (FruitShop) getChosenDepartmentStoreForOrdering();
        fruitShop.priceNameOfCommoditiesAndPrice();
        int choice;
        System.out.println("Pick a commodity to order");
        choice = checkOption(fruitShop.getMenu().size());
        --choice;
        scanAndSetAmount(fruitShop.getMenu().get(choice).getCount(), fruitShop);
        orderInterval = fruitShop.showAndChooseDeliveryTimes();
        if(fruitShop.getDeliveries() != null) {
            fruitShop.getMenu().get(choice).setCount(fruitShop.getMenu().get(choice).getCount() - 1); //vaqti hajmo lahaz kardam
            super.orderCommodity(customer, dataStore);
            setRateForCommodity(choice,fruitShop);
            customer.addToOrders(this);
            dataStore.addOrderCommodity(this);
        } else {
            System.out.println("There is no delivery.Sorry try later :(");
        }
    }

    public void scanAndSetAmount(int amountInFruitShop, FruitShop fruitShop) {
        int amount;
        while (true) {
            System.out.println("Max Of Weight To Deliver for " + fruitShop.getName() + " is " + fruitShop.getMaxOfWeightToDeliver());
            System.out.print("Set amount: ");
            amount = MyScanner.getInstance().nextInt();
            if(amount > 0 && amount <= amountInFruitShop && amount <= fruitShop.getMaxOfWeightToDeliver()) {
                this.amount = amount;
                break;
            } else {
                System.out.println("Invalid choice!Try again.");
            }
        }
    }
}