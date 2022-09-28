package ir.ac.kntu.stuff;

import ir.ac.kntu.helperclasses.BakingTime;
import ir.ac.kntu.helperclasses.MyScanner;

import java.util.ArrayList;

public class Food extends Commodity {
    private ArrayList<BakingTime> bakingTime;

    public Food() {
    }

    public String getName() {
        return super.getName();
    }

    public int getPrice() {
        return super.getPrice();
    }

    public double rateTheFood() {
        return super.getAverageOfRatings();
    }



    public void scanAndSetBakingTime() {
        ArrayList<BakingTime> times = new ArrayList<>();
        System.out.println("Choose Baking time (Multiple choices available): ");
        int numberOfBreakfast, numberOfLunch, numberOfDinner;
        numberOfBreakfast = numberOfDinner = numberOfLunch = 0;
        for(int i = 0; i < 3; i++) {
            System.out.print("1.Breakfast\n2.Lunch\n3.Dinner\nYour choice: ");
            BakingTime bakingTime;
            bakingTime = scanBakingTime();
            switch (bakingTime) {
                case BREAKFAST:
                    if(numberOfBreakfast == 0) {
                        times.add(BakingTime.BREAKFAST);
                        System.out.println("Breakfast added successfully.");
                    } else {
                        System.out.println("Already exist.");
                    }
                    numberOfBreakfast++;
                    break;
                case LUNCH:
                    if(numberOfLunch == 0) {
                        times.add(BakingTime.LUNCH);
                        System.out.println("Lunch added successfully.");
                    } else {
                        System.out.println("Already exist.");
                    }
                    numberOfLunch++;
                    break;
                case DINNER:
                    if (numberOfDinner == 0) {
                        times.add(BakingTime.DINNER);
                        System.out.println("Dinner added successfully.");
                    } else {
                        System.out.println("Already exist.");
                    }
                    numberOfDinner++;
                    break;
                default:
                    break;
            }
            System.out.print("Do you want to add another time?(Y/N) ");
            String anotherOrNot = MyScanner.getInstance().next();
            if(!(anotherOrNot.matches("\\s*[Yy]\\s*"))) {
                break;
            }
        }
        this.bakingTime = times;
    }

    private BakingTime scanBakingTime() {
        BakingTime[] options = BakingTime.values();
        while (true) {
            int userInput = MyScanner.getInstance().nextInt();
            userInput--;
            if (userInput >= 0 && userInput < options.length) {
                return options[userInput];
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    @Override
    public void defineCommodity() {
        super.defineCommodity();
        scanAndSetBakingTime();
        //Add again or not? I don't know it will update that or not.//
    }
}
