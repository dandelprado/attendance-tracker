package model;

public class StudentAttendance {
    private String studentNumber;
    private String timeInCode;
    private String timeOutCode;
    private String generatedTimeInCode;
    private String generatedTimeOutCode;
    private int absences;

    public StudentAttendance(String studentNumber, String timeInCode, String timeOutCode, String generatedTimeInCode,
            String generatedTimeOutCode) {
        this.studentNumber = studentNumber;
        this.timeInCode = timeInCode;
        this.timeOutCode = timeOutCode;
        this.generatedTimeInCode = generatedTimeInCode;
        this.generatedTimeOutCode = generatedTimeOutCode;
        this.absences = 0;
    }

    public void incrementAbsence() {
        this.absences++;
    }

    public String getGeneratedTimeInCode() {
        return generatedTimeInCode;
    }

    public String getGeneratedTimeOutCode() {
        return generatedTimeOutCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getTimeInCode() {
        return timeInCode;
    }

    public String getTimeOutCode() {
        return timeOutCode;
    }

    public int getAbsences() {
        return absences;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }
}
