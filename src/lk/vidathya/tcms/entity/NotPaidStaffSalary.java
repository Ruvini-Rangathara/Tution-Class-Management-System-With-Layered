package lk.vidathya.tcms.entity;

import java.io.Serializable;

public class NotPaidStaffSalary implements SuperEntity {
    private String staffId;
    private int year;
    private String month;
    private double salary;

    public NotPaidStaffSalary() {
    }

    public NotPaidStaffSalary(String staffId, int year, String month, double salary) {
        this.staffId = staffId;
        this.year = year;
        this.month = month;
        this.salary = salary;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "NotPaidStaffSalary{" +
                "staffId='" + staffId + '\'' +
                ", year=" + year +
                ", month='" + month + '\'' +
                ", salary=" + salary +
                '}';
    }
}
