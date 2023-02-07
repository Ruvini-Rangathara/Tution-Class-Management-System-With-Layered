package lk.vidathya.tcms.dto;

public class RegistrationPaymentDTO implements SuperDTO {
    private int code;
    private double fee;
    private String studentId;
    private int year;
    private String month;
    private String date;
    private String staffId;

    public RegistrationPaymentDTO() {
    }

    public RegistrationPaymentDTO(int code,double fee, String studentId, int year, String month, String date, String staffId) {
        this.setCode(code);
        this.fee = fee;
        this.studentId = studentId;
        this.year = year;
        this.month = month;
        this.date = date;
        this.staffId = staffId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "RegistrationPayment{" +
                "fee=" + fee +
                ", studentId='" + studentId + '\'' +
                ", year=" + year +
                ", month='" + month + '\'' +
                ", date='" + date + '\'' +
                ", staffId='" + staffId + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
