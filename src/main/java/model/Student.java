package model;

import java.util.Date;

public class Student {
    private String firstName;
    private String lastName;
    private String studentNumber;
    private String email;
    private String contactNumber;
    private Date birthday;
    private int yearLevel;

    public Student(String firstName, String lastName, String studentNumber, String email,
            String contactNumber, Date birthday, int yearLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
        this.contactNumber = contactNumber;
        this.birthday = birthday;
        this.yearLevel = yearLevel;
    }


    public String formatStudentInfo(boolean includeFirstName, boolean includeLastName,
            boolean includeStudentNumber, boolean includeEmail,
            boolean includeContactNumber, boolean includeBirthday,
            boolean includeYearLevel) {
        StringBuilder formattedInfo = new StringBuilder();
        if (includeFirstName) {
            formattedInfo.append("First Name: ").append(firstName).append("\n");
        }
        if (includeLastName) {
            formattedInfo.append("Last Name: ").append(lastName).append("\n");
        }
        if (includeStudentNumber) {
            formattedInfo.append("Student Number: ").append(studentNumber).append("\n");
        }
        if (includeEmail) {
            formattedInfo.append("Email: ").append(email).append("\n");
        }
        if (includeContactNumber) {
            formattedInfo.append("Contact Number: ").append(contactNumber).append("\n");
        }
        if (includeBirthday) {
            formattedInfo.append("Birthday: ").append(birthday).append("\n");
        }
        if (includeYearLevel) {
            formattedInfo.append("Year Level: ").append(yearLevel).append("\n");
        }
        return formattedInfo.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
}