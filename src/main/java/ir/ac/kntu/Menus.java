package ir.ac.kntu;

import ir.ac.kntu.helperclasses.DataStore;
import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.markets.FruitShop;
import ir.ac.kntu.markets.Restaurant;
import ir.ac.kntu.markets.SuperMarket;
import ir.ac.kntu.order.OrderFood;
import ir.ac.kntu.order.OrderFruit;
import ir.ac.kntu.order.OrderStuff;
import ir.ac.kntu.people.Admin;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.people.Delivery;
import ir.ac.kntu.people.Supervisor;

public class Menus {
    private final DataStore dataStore;


    public Menus() {
        dataStore = new DataStore();
    }

    public void startingMenu() {
        boolean shouldBreak = false;
        while (true) {
            System.out.println("\n** Starting menu **");
            System.out.println("1. LOGIN");
            System.out.println("2. SUBMIT");
            System.out.println("3. EXIT");
            int choice = checkOption(3);
            switch (choice) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    submitMenu();
                    break;
                case 3:
                    shouldBreak = true;
                    break;
                default:
                    break;
            }
            if(shouldBreak) {
                System.out.println("\nThanks for using FARIFOOD\n" +
                                   "    *** GOOD LUCK ***   ");
                break;
            }
        }
    }

    public void submitMenu() {
        System.out.println("\n** SUBMIT MENU **");
        System.out.println("1. SUBMIT AS ADMIN");
        System.out.println("2. SUBMIT AS SUPERVISOR");
        System.out.println("3. SUBMIT AS CUSTOMER");
        System.out.println("4. EXIT");
        int choice = checkOption(4);
        switch (choice) {
            case 1:
                Admin newAdmin = new Admin();
                newAdmin.defineUser(dataStore);
                dataStore.addUserInfo(newAdmin);
                break;
            case 2:
                Supervisor newSupervisor = new Supervisor();
                newSupervisor.defineUser(dataStore);
                dataStore.addUserInfo(newSupervisor);
                dataStore.addDepartmentInfo(newSupervisor.getWorkingDepartmentStore());
                break;
            case 3:
                Customer newCustomer = new Customer();
                newCustomer.defineUser(dataStore);
                dataStore.addCustomerInfo(newCustomer);
                break;
            case 4:
            default:
                break;
        }
    }

    public void loginMenu() {
        Admin enteredUser = new Admin();
        boolean exist = false;
        System.out.println("\n** Login **\nFill the fallowing parts to logon: ");
        while (true) {
            System.out.print("Username: ");
            String username = MyScanner.getInstance().next();
            System.out.print("Password: ");
            String password = MyScanner.getInstance().next();
            for (int i = 0; i < dataStore.getUsers().size(); i++) {
                if(password.equals(dataStore.getUsers().get(i).getPassword()) && username.equals(dataStore.getUsers().get(i).getUsername())) {
                    enteredUser = dataStore.getUsers().get(i);
                    exist = true;
                    break;
                }
            }
            if(exist) {
                System.out.println("Successfully logged in!");
                break;
            } else {
                System.out.println("Something is wrong try again.");
            }
        }
        goToAppropriateMenu(enteredUser);
    }

    private void goToAppropriateMenu(Admin user) {
        Admin adminTest = new Admin();
        Customer customerTest = new Customer();
        if(user.getClass() == adminTest.getClass()) {
            adminMainMenu();
        } else if(user.getClass() == customerTest.getClass()) {
            customerMainMenu((Customer) user);
        } else {
            superVisorMainMenu((Supervisor) user);
        }
    }

    public void superVisorMainMenu(Supervisor superVisor) {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** SuperVisor Menu **");
            System.out.println("1. Info");
            System.out.println("2. Edit info");
            System.out.println("3. Edit Department info");
            System.out.println("4. Exit");
            System.out.print("Pick a number: ");
            int choice = checkOption(4);
            switch (choice) {
                case 1:
                    superVisor.showInfo();
                    break;
                case 2:
                    superVisor.editUser(dataStore);
                    break;
                case 3:
                    superVisor.getWorkingDepartmentStore().editDepartment(dataStore.getAddresses());
                case 4:
                    shouldBreak = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void customerMainMenu(Customer customer) {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** Customer Menu **");
            System.out.println("1. Order Commodity");
            System.out.println("2. Info");
            System.out.println("3. Edit info");
            System.out.println("4. Submit as special customer");
            System.out.println("5. Exit");
            System.out.print("Pick a number: ");
            int choice = checkOption(3);
            switch (choice) {
                case 1:
                    customerOrderMenu(customer);
                    break;
                case 2:
                    customer.showInfo();
                    break;
                case 3:
                    customer.editUser(dataStore);
                case 4:
                    chooseSuperMarket(customer);
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void chooseSuperMarket(Customer customer) {
        for (int i = 0, j = 0; i < dataStore.getSuperMarkets().size(); i++, j++) {
            System.out.println(j + ". " + dataStore.getSuperMarkets().get(i).getName());
        }
        int choice = checkOption(dataStore.getSuperMarkets().size());
        --choice;
        SuperMarket superMarket = (SuperMarket) dataStore.getSuperMarkets().get(choice);
        dataStore.removeDepartment(choice);
        superMarket.addSpecialCustomer(customer);
        dataStore.addDepartmentStore(superMarket);
    }

    public void customerOrderMenu(Customer customer) {
        int choice;
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** Customer Order Menu **");
            System.out.println("1.Order Food");
            System.out.println("2.Order Fruit");
            System.out.println("3.Order Stuff");
            System.out.println("4.Exit");
            choice = checkOption(4);
            switch (choice) {
                case 1:
                    OrderFood orderFood = new OrderFood();
                    orderFood.orderMenu(customer, dataStore);
                    dataStore.addOrderCommodity(orderFood);
                    break;
                case 2:
                    OrderFruit orderFruit = new OrderFruit();
                    orderFruit.orderMenu(customer, dataStore);
                    dataStore.addOrderCommodity(orderFruit);
                    break;
                case 3:
                    OrderStuff orderStuff = new OrderStuff();
                    orderStuff.orderMenu(customer, dataStore);
                    dataStore.addOrderCommodity(orderStuff);
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void adminOrderMenu() {
        int choice;
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** Admin Order Menu **");
            System.out.println("1.Order Food");
            System.out.println("2.Order Fruit");
            System.out.println("3.Order Stuff");
            System.out.println("4.Exit");
            choice = checkOption(4);
            switch (choice) {
                case 1:
                    OrderFood orderFood = new OrderFood();
                    orderFood.orderMenu(dataStore);
                    dataStore.addOrderCommodity(orderFood);
                    break;
                case 2:
                    OrderFruit orderFruit = new OrderFruit();
                    orderFruit.orderMenu(dataStore);
                    dataStore.addOrderCommodity(orderFruit);
                    break;
                case 3:
                    OrderStuff orderStuff = new OrderStuff();
                    orderStuff.orderMenu(dataStore);
                    dataStore.addOrderCommodity(orderStuff);
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void adminMainMenu() {
        boolean shouldBreak = true;
        int choice;
        while (shouldBreak) {
            System.out.println("\n** Admin Menu **");
            System.out.println("1. Order commodity");
            System.out.println("2. Info menu");
            System.out.println("3. Define menu");
            System.out.println("4. Edit menu");
            System.out.println("5. Delivery menu");
            System.out.println("6. Exit");
            choice = checkOption(6);
            switch (choice) {
                case 1:
                    adminOrderMenu();
                    break;
                case 2:
                    infoMenu();
                    break;
                case 3:
                    defineMenu();
                    break;
                case 4:
                    editMenu();
                    break;
                case 5:
                    deliveryMenu();
                case 6:
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void editMenu() {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** EditMenu **\n1.Edit Customers Info");
            System.out.println("2.Edit Supervisors Info");
            System.out.println("3.Edit FruitShops Info");
            System.out.println("4.Edit Restaurants Info");
            System.out.println("5.Edit SuperMarkets Info");
            System.out.println("6.Exit");
            int choice = checkOption(6);
            int index;
            switch (choice) {
                case 1:
                    dataStore.printCustomersInfo();
                    index = checkOption(dataStore.getCustomers().size());
                    dataStore.getCustomers().get(--index).editUser(dataStore);
                    break;
                case 2:
                    dataStore.printSupervisorsInfo();
                    index = checkOption(dataStore.getSupervisors().size());
                    dataStore.getSupervisors().get(--index).editUser(dataStore);
                    break;
                case 3:
                    dataStore.printFruitShopsInfo();
                    index = checkOption(dataStore.getFruitShops().size());
                    dataStore.getFruitShops().get(--index).editDepartment(dataStore.getAddresses());
                    break;
                case 4:
                    dataStore.printRestaurantsInfo();
                    index = checkOption(dataStore.getRestaurants().size());
                    dataStore.getRestaurants().get(--index).editDepartment(dataStore.getAddresses());
                    break;
                case 5:
                    dataStore.printSupermarketsInfo();
                    index = checkOption(dataStore.getSuperMarkets().size());
                    dataStore.getSuperMarkets().get(--index).editDepartment(dataStore.getAddresses());
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void infoMenu() {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** EditMenu **\n1.Customers Info");
            System.out.println("2.Supervisors Info");
            System.out.println("3.FruitShops Info");
            System.out.println("4.Restaurants Info");
            System.out.println("5.SuperMarkets Info");
            System.out.println("6.Exit");
            int choice = checkOption(6);
            switch (choice) {
                case 1:
                    dataStore.printCustomersInfo();
                    break;
                case 2:
                    dataStore.printSupervisorsInfo();
                    break;
                case 3:
                    dataStore.printFruitShopsInfo();
                    break;
                case 4:
                    dataStore.printRestaurantsInfo();
                    break;
                case 5:
                    dataStore.printSupermarketsInfo();
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void defineMenu() {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** DefineMenu **\n1.Add customer");
            System.out.println("2.Add Supervisor");
            System.out.println("3.Add FruitShop");
            System.out.println("4.Add Restaurant");
            System.out.println("5.Add SuperMarket");
            System.out.println("6. Exit");
            int choice = checkOption(6);
            switch (choice) {
                case 1:
                    Customer newCustomer = new Customer();
                    newCustomer.defineUser(dataStore);
                    dataStore.addUserInfo(newCustomer);
                    break;
                case 2:
                    Supervisor newSupervisor = new Supervisor();
                    newSupervisor.defineUser(dataStore);
                    dataStore.addUserInfo(newSupervisor);
                    break;
                case 3:
                    FruitShop newFruitShop = new FruitShop();
                    newFruitShop.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
                    dataStore.addDepartmentInfo(newFruitShop);
                    break;
                case 4:
                    Restaurant newRestaurant = new Restaurant();
                    newRestaurant.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
                    dataStore.addDepartmentInfo(newRestaurant);
                    break;
                case 5:
                    SuperMarket newSuperMarket = new SuperMarket();
                    newSuperMarket.defineDepartmentStore(dataStore.getDeliveries(), dataStore.getAddresses());
                    dataStore.addDepartmentInfo(newSuperMarket);
                    break;
                default:
                    shouldBreak = false;
                    break;
            }
        }
    }

    public void deliveryMenu() {
        boolean shouldBreak = true;
        while (shouldBreak) {
            System.out.println("\n** Delivery Menu **");
            System.out.println("1. About deliveries");
            System.out.println("2. Add a delivery");
            System.out.println("3. Edit info of a delivery");
            System.out.println("4. Number of submitted deliveries");
            System.out.println("5. Back");
            System.out.print("Pick a number: ");
            int option = checkOption(5);
            Delivery newDelivery = new Delivery();
            switch (option) {
                case 1:
                    System.out.println("Information of Deliveries:");
                    System.out.println(dataStore.getDeliveries().toString());
                    break;
                case 2:
                    newDelivery.defineDelivery();
                    break;
                case 3:
                    editDeliveryMenu();
                    break;
                case 4:
                    System.out.println("Number of deliveries: " + dataStore.getDeliveries().size());
                    break;
                case 5:
                    shouldBreak = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void editDeliveryMenu() {
        for(int i = 0; i < dataStore.getDeliveries().size(); i++) {
            dataStore.getDeliveries().get(i).printInfo(i+1);
        }
        System.out.print("Choose one of the deliveries above to edit information :");
        int option = checkOption(dataStore.getDeliveries().size());
        System.out.println(dataStore.getSpecificDelivery(--option).toString());
        dataStore.getSpecificDelivery(option).editDelivery();
        System.out.println("Successfully edited!");
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
}
