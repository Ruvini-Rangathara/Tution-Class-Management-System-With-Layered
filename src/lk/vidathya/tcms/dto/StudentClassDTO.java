package lk.vidathya.tcms.dto;

public class StudentClassDTO implements SuperDTO{
    private String studentId;
    private String classCode;
    private String guardianNic;
    private String date;

    public StudentClassDTO() {
    }

    public StudentClassDTO(String studentId, String classCode, String guardianNic, String date) {
        this.studentId = studentId;
        this.classCode = classCode;
        this.guardianNic = guardianNic;
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getGuardianNic() {
        return guardianNic;
    }

    public void setGuardianNic(String guardianNic) {
        this.guardianNic = guardianNic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "studentId='" + studentId + '\'' +
                ", classCode='" + classCode + '\'' +
                ", guardianNic='" + guardianNic + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
