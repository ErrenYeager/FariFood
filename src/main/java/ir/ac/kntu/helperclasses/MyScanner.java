package ir.ac.kntu.helperclasses;

import java.util.Scanner;

public class MyScanner {

    private static MyScanner instance = new MyScanner();

    private Scanner scanner;

    private MyScanner() {
        scanner = new Scanner(System.in);
    }

    public static MyScanner getInstance() {
        return instance;
    }

    public String next() {
        return scanner.next();
    }

    public Double nextDouble() {
        return scanner.nextDouble();
    }

    public Integer nextInt() {
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}
