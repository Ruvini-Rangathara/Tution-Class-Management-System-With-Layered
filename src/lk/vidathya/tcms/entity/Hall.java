package lk.vidathya.tcms.entity;

import java.io.Serializable;

public class Hall implements SuperEntity {

    private String hallNo;
    private int capacity;
    private String availableFacilities;

    public Hall() {
    }

    public Hall(String hallNo, int capacity, String availableFacilities) {
        this.hallNo = hallNo;
        this.capacity = capacity;
        this.availableFacilities = availableFacilities;
    }

    public String getHallNo() {
        return hallNo;
    }

    public void setHallNo(String hallNo) {
        this.hallNo = hallNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAvailableFacilities() {
        return availableFacilities;
    }

    public void setAvailableFacilities(String availableFacilities) {
        this.availableFacilities = availableFacilities;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "hallNo='" + hallNo + '\'' +
                ", capacity='" + capacity + '\'' +
                ", availableFacilities='" + availableFacilities + '\'' +
                '}';
    }
}
