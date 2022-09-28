package ir.ac.kntu.helperclasses;

public enum DepartmentType {
    ECONOMIC(1), NORMAL(2), LUXURY(3);

    private int value;

    DepartmentType(int value) {
        this.value = value;
    }
}
