package ir.ac.kntu.stuff;

import ir.ac.kntu.helperclasses.MyScanner;
import ir.ac.kntu.helperclasses.Rating;

import java.util.ArrayList;

public class Commodity {
    private String name;
    private int price;
    private double averageOfRatings;
    private final ArrayList<Rating> ratings = new ArrayList<>();

    public Commodity() {
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void scanAndSetName() {
        System.out.print("Name: ");
        String name = MyScanner.getInstance().next();
        if(name != null && name.matches("\\s*[a-zA-Z]+\\s*")) {
            this.name = name;
        } else {
            System.out.println("Invalid input.Try again.");
            scanAndSetName();
        }
    }

    public void scanAndSetPrice() {
        System.out.print("\nPrice : ");
        int price = MyScanner.getInstance().nextInt();
        if(price > 0) {
            this.price = price;
        } else {
            System.out.println("Invalid input.Try again.");
            scanAndSetPrice();
        }
    }

    public void rateTheCommodity(Rating rate) {
        ratings.add(rate);
    }

    public double getAverageOfRatings() {
        averageOfRatings = 0;
        for (Rating rating : ratings) {
            averageOfRatings += rating.getValue();
        }
        averageOfRatings /= ratings.size();
        return averageOfRatings;
    }

    public void defineCommodity() {
        System.out.println("Fill following parts :");
        scanAndSetName();
        scanAndSetPrice();
    }
}
