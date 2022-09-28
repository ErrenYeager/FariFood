package ir.ac.kntu.helperclasses;

import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.markets.FruitShop;
import ir.ac.kntu.markets.Restaurant;
import ir.ac.kntu.markets.SuperMarket;
import ir.ac.kntu.people.Admin;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.people.Delivery;
import ir.ac.kntu.people.Supervisor;
import ir.ac.kntu.stuff.Commodity;
import ir.ac.kntu.order.OrderCommodity;

import java.util.ArrayList;

public class DataStore {
    private final ArrayList<Delivery> deliveries = new ArrayList<>();
    private final ArrayList<Admin> users = new ArrayList<>();
    private final ArrayList<String> passwords = new ArrayList<>();
    private final ArrayList<String> usernames = new ArrayList<>();
    private final ArrayList<DepartmentStore> departmentStores = new ArrayList<>();
    private final ArrayList<Commodity> commodities = new ArrayList<>();
    private final ArrayList<Address> addresses = new ArrayList<>();
    private final ArrayList<OrderCommodity> orderCommodities = new ArrayList<>();


    public void printRestaurantsInfo() {
        for(int i = 0, j = 1; i < getRestaurants().size(); i++, j++) {
            System.out.println("Number " + j + ". " + getRestaurants().get(i).toString());
        }
    }

    public void printSupermarketsInfo() {
        for(int i = 0, j = 1; i < getSuperMarkets().size(); i++, j++) {
            System.out.println("Number " + j + ". " + getSuperMarkets().get(i).toString());
        }
    }

    public void printFruitShopsInfo() {
        for(int i = 0, j = 1; i < getFruitShops().size(); i++, j++) {
            System.out.println("Number " + j + ". " + getFruitShops().get(i).toString());
        }
    }

    public void printCustomersInfo() {
        for(int i = 0, j = 1; i < getCustomers().size(); i++, j++) {
            System.out.println("Number " + j + ". " + getCustomers().get(i).toString());
        }
    }

    public void printSupervisorsInfo() {
        for(int i = 0, j = 1; i < getSupervisors().size(); i++, j++) {
            System.out.println("Number " + j + ". " + getSupervisors().get(i).toString());
        }
    }

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }

    public void addDepartmentStore(DepartmentStore departmentStore) {
        departmentStores.add(departmentStore);
    }

    public void addOrderCommodity(OrderCommodity orderCommodity) {
        orderCommodities.add(orderCommodity);
    }

    public void removeDepartment(int index) {
        departmentStores.remove(index);
    }

    public void addDepartmentInfo(DepartmentStore departmentStore) {
        departmentStores.add(departmentStore);
        for(int i = 0; i < departmentStore.getDeliveries().size(); i++) {
            deliveries.add(departmentStore.getDeliveries().get(i));
        }
    }

    public Delivery getSpecificDelivery(int index) {
        return deliveries.get(index);
    }

    public DepartmentStore getSpecificDepartment(int index) {
        return departmentStores.get(index);
    }

    public void addUserInfo(Admin admin) {
        users.add(admin);
        usernames.add(admin.getUsername());
        passwords.add(admin.getPassword());
    }

    public void addCustomerInfo(Admin admin) {
        users.add(admin);
        usernames.add(admin.getUsername());
        passwords.add(admin.getPassword());
        Customer customer = (Customer)admin;
        addresses.add(customer.getAddress());
    }

    public ArrayList<Delivery> getDeliveries() {
        ArrayList<Delivery> copyOfDeliveries = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            copyOfDeliveries.add(delivery);
        }
        return copyOfDeliveries;
    }

    public ArrayList<Admin> getUsers() {
        ArrayList<Admin> copyOfUsers = new ArrayList<>();
        for (Admin user : users) {
            copyOfUsers.add(user);
        }
        return copyOfUsers;
    }

    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer temp = new Customer();
        for (Admin user : users) {
            if (user.getClass() == temp.getClass()) {
                customers.add((Customer) user);
            }
        }
        return customers;
    }

    public ArrayList<Supervisor> getSupervisors() {
        ArrayList<Supervisor> supervisors = new ArrayList<>();
        Supervisor temp = new Supervisor();
        for (Admin user : users) {
            if (user.getClass() == temp.getClass()) {
                supervisors.add((Supervisor) user);
            }
        }
        return supervisors;
    }

    public ArrayList<String> getPasswords() {
        ArrayList<String> copyOfPasswords = new ArrayList<>();
        for (String password : passwords) {
            copyOfPasswords.add(password);
        }
        return copyOfPasswords;
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> copyOfUsernames = new ArrayList<>();
        for (String username : usernames) {
            copyOfUsernames.add(username);
        }
        return copyOfUsernames;
    }

    public ArrayList<DepartmentStore> getDepartmentStores() {
        ArrayList<DepartmentStore> copyOfDepartments = new ArrayList<>();
        for (DepartmentStore departmentStore : departmentStores) {
            copyOfDepartments.add(departmentStore);
        }
        return copyOfDepartments;
    }

    public ArrayList<Address> getAddresses() {
        ArrayList<Address> copyOfAddresses = new ArrayList<>();
        for (Address address : addresses) {
            copyOfAddresses.add(address);
        }
        return copyOfAddresses;
    }

    public ArrayList<DepartmentStore> getFruitShops() {
        FruitShop test = new FruitShop();
        ArrayList<DepartmentStore> fruitShops = new ArrayList<>();
        for (DepartmentStore departmentStore : departmentStores) {
            if (departmentStore.getClass() == test.getClass()) {
                fruitShops.add(departmentStore);
            }
        }
        return fruitShops;
    }

    public ArrayList<DepartmentStore> getRestaurants() {
        Restaurant test = new Restaurant();
        ArrayList<DepartmentStore> restaurants = new ArrayList<>();
        for (DepartmentStore departmentStore : departmentStores) {
            if (departmentStore.getClass() == test.getClass()) {
                restaurants.add(departmentStore);
            }
        }
        return restaurants;
    }

    public ArrayList<DepartmentStore> getSuperMarkets() {
        SuperMarket test = new SuperMarket();
        ArrayList<DepartmentStore> superMarkets = new ArrayList<>();
        for (DepartmentStore departmentStore : departmentStores) {
            if (departmentStore.getClass() == test.getClass()) {
                superMarkets.add(departmentStore);
            }
        }
        return superMarkets;
    }
}
