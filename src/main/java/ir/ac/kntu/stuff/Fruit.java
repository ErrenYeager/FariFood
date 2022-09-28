package ir.ac.kntu.stuff;

import ir.ac.kntu.helperclasses.MyScanner;

public class Fruit extends Commodity{
    private int count;

    public Fruit() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void scanAndSetCount() {
        while (true) {
            System.out.print("Count: ");
            int count = MyScanner.getInstance().nextInt();
            if (count > 0) {
                setCount(count);
                System.out.println("Successfully set!");
                break;
            } else {
                System.out.println("The count should be at least 1.");
            }
        }
    }

    @Override
    public void defineCommodity() {
        super.defineCommodity();
        scanAndSetCount();
    }
}
