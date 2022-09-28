package ir.ac.kntu.helperclasses;

import java.util.ArrayList;
import java.util.Objects;

public class Address {

    private int plaque;

    private String alleyName;

    private String streetName;

    private String city;

    public Address() {
    }

    public void defineAddress(ArrayList<Address> definedAddresses) {
        while (true){
            System.out.println("\nAddress :");
            System.out.print("Plaque: ");
            int plaque = MyScanner.getInstance().nextInt();
            setPlaque(plaque);
            System.out.print("Alley name: ");
            String alley = MyScanner.getInstance().next();
            setAlleyName(alley);
            System.out.print("Street name: ");
            String street = MyScanner.getInstance().next();
            setStreetName(street);
            System.out.print("City: ");
            String city = MyScanner.getInstance().next();
            setCity(city);
            boolean exist = false;
            for (Address address : definedAddresses) {
                if (this.equals(address)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                System.out.println("Address already exists! Define another.");
            } else {
                break;
            }
        }
    }

    public void defineAddress() {
        System.out.println("\nAddress :");
        System.out.print("Plaque: ");
        int plaque = MyScanner.getInstance().nextInt();
        setPlaque(plaque);
        System.out.print("Alley name: ");
        String alley = MyScanner.getInstance().next();
        setAlleyName(alley);
        System.out.print("Street name: ");
        String street = MyScanner.getInstance().next();
        setStreetName(street);
        System.out.print("City: ");
        String city = MyScanner.getInstance().next();
        setCity(city);
    }

    public void setPlaque(int plaque) {
        if(plaque > 0) {
            this.plaque = plaque;
        } else {
            System.out.print("Invalid plaque!Try again\nPlaque: ");
            plaque = MyScanner.getInstance().nextInt();
            setPlaque(plaque);
        }
    }

    public void setAlleyName(String alleyName) {
        if(alleyName != null) {
            this.alleyName = alleyName;
        } else {
            System.out.print("Alley name cannot be null.Try again.\nAlley name: ");
            alleyName = MyScanner.getInstance().next();
            setAlleyName(alleyName);
        }
    }

    public void setStreetName(String streetName) {
        if(alleyName != null) {
            this.streetName = streetName;
        } else {
            System.out.print("Street name cannot be null.Try again.\nStreet name: ");
            streetName = MyScanner.getInstance().next();
            setStreetName(streetName);
        }
    }

    public void setCity(String city) {
        if(city != null) {
            this.city = city;
        } else {
            System.out.print("City name cannot be null\nCity name: ");
            city = MyScanner.getInstance().next();
            setCity(city);
        }
    }

    @Override
    public String toString() {
        return plaque + " - " + alleyName + " - " + streetName + " - " + city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return plaque == address.plaque && Objects.equals(alleyName, address.alleyName) && Objects.equals(streetName, address.streetName) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaque, alleyName, streetName, city);
    }
}
