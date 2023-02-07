package lk.vidathya.tcms.entity;

import java.io.Serializable;

public class StudentClass  implements SuperEntity {
    private String studentId;
    private String classCode;
    private String guardianNic;
    private String date;

    public StudentClass() {
    }

    public StudentClass(String studentId, String classCode, String guardianNic, String date) {
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
