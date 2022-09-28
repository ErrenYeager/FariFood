package ir.ac.kntu.helperclasses;

public enum Rating {
    EXCELLENT(5), GOOD(4), AVERAGE(3), NOT_BAD(2), AWFUL(1);

    private int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Rating getEnum(int value) {
        if(value == 1) {
            return AWFUL;
        } else if(value == 2) {
            return NOT_BAD;
        } else if(value == 3) {
            return AVERAGE;
        } else if(value == 4) {
            return GOOD;
        } else {
            return EXCELLENT;
        }
    }
}
