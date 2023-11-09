package phoneBookPackage;
// omvandlas till byte för lagring eller överföring.
import java.io.Serializable;

class Address implements Serializable {
    // privata fälter att spara data i
    private String street;
    private String city;
    private String postCode;
    private String apartmentNumber;


    public Address(String street, String city, String PostCode, String Apartment) {
        //konstruktör att initializa en address objekt med info
        this.street = street;
        this.city= city;
        this.postCode= PostCode;
        this.apartmentNumber = Apartment;

    }
    // setter metoder att updatera street, city,..
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPostCode(String postCode) {
        this.street = postCode;
    }
    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
    @Override
    // detta metoden att skapa en string till adress objekt
    public String toString() {
        return  street +" "+ apartmentNumber + "\n" + postCode + " "+ city + "." ;
    }
}
