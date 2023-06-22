package tcf.com.tcp.curriculumdesign2.server.model;

import androidx.annotation.NonNull;

public class Scheduling extends BaseModel{
    private int capacity;
    private String location;
    private double charge;

    public Scheduling(){
        super();
    }
    public Scheduling(Long id){
        this();
        setId(id);
    }

    @NonNull
    @Override
    public Scheduling clone(){
        try {
            Scheduling scheduling= (Scheduling) super.clone();
            return scheduling;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new Scheduling();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
