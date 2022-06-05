package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.hostel.Block;
import com.hostel.Hostel;
import com.hostel.Room;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.student.Department;
import com.student.Student;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file path given");
            System.exit(1);
        }
        int ROOM_SIZE = Integer.parseInt(args[1]);
        try {
            FileReader filereader = new FileReader(args[0]);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            List<Student> studentList = new ArrayList<>();
            Hostel hostel = new Hostel(ROOM_SIZE);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                studentList.add(
                        new Student(nextRecord[1], Department.valueOf(nextRecord[2]), Integer.parseInt(nextRecord[3])));
            }
            Iterator<Student> iterator = studentList.iterator();
            while (iterator.hasNext()) {
                hostel.allocateRoom(iterator.next());
            }
            FileWriter fileWriter = new FileWriter("result.csv");
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(new String[] { "Block Name", "Room Number", "Student Name 1", "Student Department 1",
                    "Student Year 1", "Student Name 2", "Student Department 2", "Student Year 2", "Student Name 3",
                    "Student Department 3", "Student Year 3", "Student Name 4", "Student Department 4",
                    "Student Year 4" });
            csvReader.close();
            Iterator<Entry<Block, List<Room>>> iterator2 = hostel.blocks.entrySet().iterator();
            while (iterator2.hasNext()) {
                Entry<Block, List<Room>> entry = iterator2.next();
                Block block = entry.getKey();
                int roomNumber = 1;
                for (Room room : entry.getValue()) {
                    String[] nextLine = new String[2 + 3 * ROOM_SIZE];
                    nextLine[0] = block.toString();
                    nextLine[1] = String.valueOf(roomNumber);
                    Student[] students = room.students;
                    int i = 2;
                    for (Student student : students) {
                        if (student == null)
                            break;
                        nextLine[i] = student.name;
                        nextLine[i + 1] = student.department.toString();
                        nextLine[i + 2] = String.valueOf(student.yearOfStudy);
                        i += 3;
                    }
                    csvWriter.writeNext(nextLine);
                    roomNumber++;
                }
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
