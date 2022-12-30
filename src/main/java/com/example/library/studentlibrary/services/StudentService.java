package com.example.library.studentlibrary.services;


import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.findById(id).get();

        return student;
    }

    public void createStudent(Student student){
        cardService4.createAndReturn(student);
        studentRepository4.save(student);
    }

    public void updateStudent(Student student){
        studentRepository4.save(student);
    }

    public void deleteStudent(int id){
        studentRepository4.deleteById(id);
        cardService4.deactivateCard(id);
        //Delete student and deactivate corresponding card
    }
}