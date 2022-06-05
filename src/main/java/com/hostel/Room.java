package com.hostel;

import com.student.Department;
import com.student.Student;

public class Room {
  public Student[] students;
  int currentSize;
  int size;

  public Room(int size) {
    students = new Student[size];
    this.currentSize = 0;
    this.size = size;
  }

  public boolean isFull() {
    return currentSize == size;
  }

  public void addStudent(Student student) {
    students[currentSize++] = student;
  }

  public int studentsOfSpecificDepartment(Department department) {
    int count = 0;
    for (Student student : students) {
      if (student == null)
        break;
      if (student.department.equals(department))
        count++;
    }
    return count;
  }

  public boolean containsSpecificDepartment(Department department) {
    for (Student student : students) {
      if (student == null)
        break;
      if (student.department.equals(department))
        return true;
    }
    return false;
  }

  public String toString() {
    String result = "( ROOM_SIZE = " + this.size + " Students: ";
    for (Student student : students) {
      if (student == null)
        break;
      result += student.department + " ";
    }
    result += " )";
    return result;
  }
}
