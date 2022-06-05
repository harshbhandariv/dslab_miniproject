package com.hostel;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.student.Department;
import com.student.Student;

public class Hostel {
  public Map<Block, List<Room>> blocks;
  private final int ROOM_SIZE;

  public Hostel(int size) {
    blocks = new HashMap<>();
    this.ROOM_SIZE = size;
    for (Block block : Block.values()) {
      blocks.put(block, new ArrayList<Room>());
    }
  }

  public void allocateRoom(Student student) {
    switch (student.yearOfStudy) {
      case 1:
        findRoomInBlock(student, blocks.get(Block.FRESHERS));
        break;
      case 2:
        findRoomInBlock(student, blocks.get(Block.SOPHOMORE));
        break;
      case 3:
        findRoomInBlock(student, blocks.get(Block.PREFINAL));
        break;
      case 4:
        findRoomInBlock(student, blocks.get(Block.FINAL));
        break;
      default:
        System.out.println("Unknown year: " + student.yearOfStudy);
    }
  }

  private void findRoomInBlock(Student student, List<Room> rooms) {
    int n = rooms.size();
    boolean roomAllocated = false;
    for (int index = 0; index < n; index++) {
      Room room = rooms.get(index);
      if (!room.isFull()) {
        if (roomMeetsAllRequirement(index, student.department, rooms)) {
          room.addStudent(student);
          roomAllocated = true;
          break;
        }
      }
    }
    if (!roomAllocated) {
      Room newRoom = new Room(ROOM_SIZE);
      newRoom.addStudent(student);
      if (rooms.size() > 0 && rooms.get(rooms.size() - 1).containsSpecificDepartment(student.department)) {
        rooms.add(new Room(ROOM_SIZE));
      }
      rooms.add(newRoom);
    }
  }

  private boolean roomMeetsAllRequirement(int index, Department department, List<Room> rooms) {
    boolean result = rooms.get(index).studentsOfSpecificDepartment(department) < ROOM_SIZE / 2;
    if (index > 0 && rooms.get(index - 1).containsSpecificDepartment(department)) {
      return false;
    }
    if (index < (rooms.size() - 1) && rooms.get(index + 1).containsSpecificDepartment(department)) {
      return false;
    }
    return result;
  }
}
