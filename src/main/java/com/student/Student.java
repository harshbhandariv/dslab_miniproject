package com.student;

public class Student {
  public int yearOfStudy;
  public String name;
  public Department department;

  public Student(String name, Department department, int yearOfStudy) {
    this.name = name;
    this.yearOfStudy = yearOfStudy;
    this.department = department;
  }

  public String toString() {
    return "(" + this.name + " " + this.yearOfStudy + " " + this.department + ")";
  }
}
