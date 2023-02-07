package lk.vidathya.tcms.dto;

public class StaffSalaryDTO implements SuperDTO{
    private String paymentCode;
    private String staffId;
    private int year;
    private String month;
    private double salary;
    private String date;
    private String payerId;

    public StaffSalaryDTO() {
    }

    public StaffSalaryDTO(String paymentCode, String staffId, int year, String month, double salary, String date, String payerId) {
        this.paymentCode = paymentCode;
        this.staffId = staffId;
        this.year = year;
        this.month = month;
        this.salary = salary;
        this.date = date;
        this.payerId = payerId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    @Override
    public String toString() {
        return "StaffSalary{" +
                "paymentCode='" + paymentCode + '\'' +
                ", staffId='" + staffId + '\'' +
                ", year=" + year +
                ", month='" + month + '\'' +
                ", salary=" + salary +
                ", date='" + date + '\'' +
                ", payerId='" + payerId + '\'' +
                '}';
    }
}
