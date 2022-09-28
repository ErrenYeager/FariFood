package ir.ac.kntu.stuff;

import ir.ac.kntu.helperclasses.MyScanner;

public class Stuff extends Commodity{
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public void scanAndSetCount() {
        while (true) {
            System.out.print("Count: ");
            int count = MyScanner.getInstance().nextInt();
            if (count > 0) {
                System.out.println("Successfully set!");
                setCount(count);
                break;
            } else {
                System.out.println("The count should be at least 1.");
            }
        }
    }

    public int getCount() {
        return count;
    }

    @Override
    public void defineCommodity() {
        super.defineCommodity();
        scanAndSetCount();
    }
}
