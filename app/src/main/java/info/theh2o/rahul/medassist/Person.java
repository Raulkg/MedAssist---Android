package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/29/2016.
 */
public class Person {
    //name and address string
    private String ID;
    private String name;
    private String address;
    private String ProfileURL;
    public Person() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getProfileURL() {
        return ProfileURL;
    }

    public void setProfileURL(String ProfileURL) {
        this.ProfileURL = ProfileURL;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}