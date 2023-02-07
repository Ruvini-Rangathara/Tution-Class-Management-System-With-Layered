package lk.vidathya.tcms.dto;

public class NotPaidTutorSalaryDTO implements SuperDTO {
    private String tutorId;
    private String classCode;
    private int year;
    private String month;
    private double salary;

    public NotPaidTutorSalaryDTO() {
    }

    public NotPaidTutorSalaryDTO(String tutorId, String classCode, int year, String month, double salary) {
        this.tutorId = tutorId;
        this.classCode = classCode;
        this.year = year;
        this.month = month;
        this.salary = salary;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
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
        return "NotPaidTutorSalary{" +
                "tutorId='" + tutorId + '\'' +
                ", classCode='" + classCode + '\'' +
                ", year=" + year +
                ", month='" + month + '\'' +
                ", salary=" + salary +
                '}';
    }
}
