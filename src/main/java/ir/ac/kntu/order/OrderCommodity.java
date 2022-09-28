package ir.ac.kntu.order;

import ir.ac.kntu.helperclasses.*;
import ir.ac.kntu.markets.DepartmentStore;
import ir.ac.kntu.people.Customer;
import ir.ac.kntu.stuff.Commodity;

import java.util.ArrayList;

public abstract class OrderCommodity {
    private Time timeOfOrdering;
    private Commodity commodity;
    private DepartmentStore chosenDepartmentStoreForOrdering;
    private Rating rateToDepartment;

    public OrderCommodity() {
    }

    public void scanAndSetTimeOfOrdering() {
        Time setTime = new Time();
        setTime.scanAndSetTime();
        this.timeOfOrdering = setTime;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public void setChosenDepartmentForOrdering(DepartmentStore chosenDepartmentStoreForOrdering) {
        this.chosenDepartmentStoreForOrdering = chosenDepartmentStoreForOrdering;
    }

    public void setRateToDepartment(Rating rateToDepartment) {
        this.rateToDepartment = rateToDepartment;
    }

    public boolean checkOpenOrClosed(DepartmentStore departmentStoreToCheck, Time time) {
        if(departmentStoreToCheck.getWorkTime()[0].getHour() <= time.getHour() && departmentStoreToCheck.getWorkTime()[1].getHour() >= time.getHour()) {
            return true;
        }else if(departmentStoreToCheck.getWorkTime()[1].getHour() == 0 && departmentStoreToCheck.getWorkTime()[0].getHour() <= time.getHour()){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<DepartmentStore> getOpenDepartments(ArrayList<DepartmentStore> definedDepartmentStores, Time time) {
        ArrayList<DepartmentStore> openDepartmentStores = new ArrayList<>();
        System.out.println("Open Departments:\n");
        for (int i = 0; i < definedDepartmentStores.size(); i++) {
            if (checkOpenOrClosed(definedDepartmentStores.get(i), time)) {
                openDepartmentStores.add(definedDepartmentStores.get(i));
            }
        }
        return openDepartmentStores;
    }

    public static void swapDepartments(ArrayList<DepartmentStore> list, int i, int j) {
        DepartmentStore temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public ArrayList<DepartmentStore> ascendingOrderRate(ArrayList<DepartmentStore> departmentStores) {
        for (int i = 2; i <= departmentStores.size(); i++) {
            for (int j = 0; j <= departmentStores.size() - i; j++) {
                if (departmentStores.get(j).getAverageOfRatings() > departmentStores.get(j + 1).getAverageOfRatings()) {
                    swapDepartments(departmentStores, j, j + 1);
                }
            }
        }
        return departmentStores;
    }

    public ArrayList<DepartmentStore> descendingOrderRate(ArrayList<DepartmentStore> departmentStores) {
        for (int i = 2; i <= departmentStores.size(); i++) {
            for (int j = 0; j <= departmentStores.size() - i; j++) {
                if (departmentStores.get(j).getAverageOfRatings() > departmentStores.get(j + 1).getAverageOfRatings()) {
                    swapDepartments(departmentStores, j, j + 1);
                }
            }
        }
        ArrayList<DepartmentStore> result = new ArrayList<>();
        for(int i = departmentStores.size()-1; i >= 0; i--) {
            result.add(departmentStores.get(i));
        }
        return result;
    }

    public ArrayList<DepartmentStore> descendingOrderCountOfRate(ArrayList<DepartmentStore> departmentStores) {
        for (int i = 2; i <= departmentStores.size(); i++) {
            for (int j = 0; j <= departmentStores.size() - i; j++) {
                if (departmentStores.get(j).getRatings().size() > departmentStores.get(j + 1).getRatings().size()) {
                    swapDepartments(departmentStores, j, j + 1);
                }
            }
        }
        ArrayList<DepartmentStore> result = new ArrayList<>();
        for(int i = departmentStores.size()-1; i >= 0; i--) {
            result.add(departmentStores.get(i));
        }
        return result;
    }

    public ArrayList<DepartmentStore> ascendingOrderCountOfRate(ArrayList<DepartmentStore> departmentStores) {
        for (int i = 2; i <= departmentStores.size(); i++) {
            for (int j = 0; j <= departmentStores.size() - i; j++) {
                if (departmentStores.get(j).getRatings().size() > departmentStores.get(j + 1).getRatings().size()) {
                    swapDepartments(departmentStores, j, j + 1);
                }
            }
        }
        return departmentStores;
    }

    public void orderMenu(Customer customer, DataStore dataStore) {
        System.out.println("Enter time you want the commodity: ");
        scanAndSetTimeOfOrdering();
        ArrayList<DepartmentStore> openDepartmentStores = getOpenDepartments(dataStore.getDepartmentStores(), timeOfOrdering);
        System.out.println("Select the method you want for searching:");
        searchingMenu(openDepartmentStores);
        orderCommodity(customer,dataStore);
    }

    public void orderMenu(DataStore dataStore) {
        System.out.println("Select the customer you want to order commodity for:");
        Customer customer = showCustomersToSet(dataStore.getCustomers());
        System.out.println("Enter time you want the commodity: ");
        scanAndSetTimeOfOrdering();
        ArrayList<DepartmentStore> openDepartmentStores = getOpenDepartments(dataStore.getDepartmentStores(), timeOfOrdering);
        System.out.println("Select the method you want for searching:");
        searchingMenu(openDepartmentStores);
        orderCommodity(customer,dataStore);
    }

    public void searchingMenu(ArrayList<DepartmentStore> openDepartmentStores) {
        System.out.println("1.List of Departments in Ascending rate order");
        System.out.println("2.List of Departments in Descending rate order");
        System.out.println("3.List of Departments in Ascending count of rate order");
        System.out.println("4.List of Departments in Descending count of rate order");
        System.out.println("5.Search by department name");
        System.out.println("6.Search by department type");
        System.out.print("Your choice: ");
        int option = chooseDepartment(6);
        searchingMethods(openDepartmentStores, option);
    }

    private void printDepartmentsList(ArrayList<DepartmentStore> departmentStores) {
        for(int i = 0; i < departmentStores.size(); i++) {
            System.out.println(i+1 + departmentStores.get(i).getName());
        }
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

    private int chooseDepartment(int maxPossible) {
        int choice;
        while (true) {
            System.out.print("Your choice: ");
            choice = MyScanner.getInstance().nextInt();
            if(choice <= maxPossible && choice > 0) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        return choice;
    }

    private DepartmentStore chooseTheFinalDepartment(ArrayList<DepartmentStore> finalDepartmentStores) {
        System.out.print("Choose a department, ");
        int option = checkOption(finalDepartmentStores.size());
        --option;
        return finalDepartmentStores.get(option);
    }

    private void searchingMethods(ArrayList<DepartmentStore> openDepartmentStores, int option) {
        ArrayList<DepartmentStore> departmentStores = new ArrayList<>();
        switch (option) {
            case 1:
                printDepartmentsList(ascendingOrderRate(openDepartmentStores));
                setChosenDepartmentForOrdering(chooseTheFinalDepartment(ascendingOrderRate(openDepartmentStores)));
                break;
            case 2:
                printDepartmentsList(descendingOrderRate(openDepartmentStores));
                setChosenDepartmentForOrdering(chooseTheFinalDepartment(descendingOrderRate(openDepartmentStores)));
                break;
            case 3:
                printDepartmentsList(ascendingOrderCountOfRate(openDepartmentStores));
                setChosenDepartmentForOrdering(chooseTheFinalDepartment(ascendingOrderCountOfRate(openDepartmentStores)));
                break;
            case 4:
                printDepartmentsList(descendingOrderCountOfRate(openDepartmentStores));
                setChosenDepartmentForOrdering(chooseTheFinalDepartment(descendingOrderCountOfRate(openDepartmentStores)));
                break;
            case 5:
                handleNameToDepartment(openDepartmentStores, departmentStores);
                break;
            case 6:
                handleTypeToDepartment(openDepartmentStores, departmentStores);
                break;
            default:
                break;

        }
    }

    private void handleNameToDepartment(ArrayList<DepartmentStore> openDepartmentStores, ArrayList<DepartmentStore> departmentStores) {
        int counter = 0;
        System.out.print("Name of the department you want: ");
        String departmentName = MyScanner.getInstance().next();
        for (int i = 0; i < openDepartmentStores.size(); i++) {
            if (openDepartmentStores.get(i).getName().equals(departmentName)) {
                System.out.println(++counter + ". " + openDepartmentStores.get(i).getName() +
                                   " - " + openDepartmentStores.get(i).getDepartmentType());
                departmentStores.add(openDepartmentStores.get(i));
            }
        }
        if(counter == 0) {
            System.out.println("There is no department with this name.");
        } else {
            setChosenDepartmentForOrdering(chooseTheFinalDepartment(departmentStores));
        }
    }

    private void handleTypeToDepartment(ArrayList<DepartmentStore> openDepartmentStores, ArrayList<DepartmentStore> finalDepartmentStores) {
        DepartmentType departmentType = getAndSetDepartmentType();
        int counter = 0;
        for (int i = 0; i < openDepartmentStores.size(); i++) {
            if (openDepartmentStores.get(i).getDepartmentType() == departmentType) {
                System.out.println(++counter + ". " + openDepartmentStores.get(i).getName());
                finalDepartmentStores.add(openDepartmentStores.get(i));
            }
        }
        if(counter == 0) {
            System.out.println("There is no department with this type.");
            searchingMenu(openDepartmentStores);
        } else {
            setChosenDepartmentForOrdering(chooseTheFinalDepartment(finalDepartmentStores));
        }
    }

    public DepartmentType getAndSetDepartmentType() {
        DepartmentType departmentType;
        System.out.println("Select department type :");
        System.out.println("1.ECONOMIC\n2.NORMAL\n3.LUXURY");
        int choice;
        while(true) {
            System.out.print("Your choice: ");
            choice = MyScanner.getInstance().nextInt();
            if (choice == 1) {
                departmentType = DepartmentType.ECONOMIC;
                break;
            } else if (choice == 2) {
                departmentType = DepartmentType.NORMAL;
                break;
            } else if (choice == 3) {
                departmentType = DepartmentType.LUXURY;
                break;
            }
            System.out.println("Just 1 or 2 or 3. Try again.");
        }
        return departmentType;
    }

    public Customer showCustomersToSet(ArrayList<Customer> customers) {
        System.out.println("List of customers:");
        int j;
        for (int i = 0; i < customers.size(); i++) {
            j = i+1;
            System.out.println(j + ". " + customers.get(i).toString());
        }
        Customer chosenCustomer;
        int choice;
        while (true) {
            System.out.print("Your choice: ");
            choice = MyScanner.getInstance().nextInt();
            if(choice >= 1 && choice <= customers.size()) {
                choice--;
                chosenCustomer = customers.get(choice);
                break;
            } else {
                System.out.println("Invalid input.Try again.");
            }
        }
        return chosenCustomer;
    }

    public DepartmentStore getChosenDepartmentStoreForOrdering() {
        return chosenDepartmentStoreForOrdering;
    }

    public void orderCommodity(Customer customer, DataStore dataStore) {
        System.out.println("Processing...");
        System.out.println("Sending...");
        System.out.println("Delivered:)");
        setRateForDepartment();
    }



    public void setRateForDepartment() {
        System.out.println(chosenDepartmentStoreForOrdering.getName());
        Rating rating = Rating.AWFUL;
        System.out.println("Rate the Department(1,2,3,4,5)");
        int rate = chooseDepartment(5);
        chosenDepartmentStoreForOrdering.rateDepartmentStore(rating.getEnum(rate));
        setRateToDepartment(rating.getEnum(rate));
    }



    @Override
    public String toString() {
        return "OrderCommodity{" +
                "timeOfOrdering=" + timeOfOrdering +
                ", nameOfCommodity='" + commodity + '\'' +
                ", ChosenDepartmentForOrdering=" + chosenDepartmentStoreForOrdering +
                '}';
    }
}
