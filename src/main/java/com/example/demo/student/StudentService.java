package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    //this is "dependency injection" it can only be done with something that has the "component" annotation
    //It essentially lets the studentRespository function like a static class even though it isn't one
    //These 'fake-static classes' are called "beans"
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student)
    {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent())
        {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId)
    {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists)
        {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        else
        {
            studentRepository.deleteById(studentId);
        }
    }

    //@Transactional means that instead of using SQL queries you can just use the setters built into the Entity to update the record in your db
    @Transactional
    public void updateStudent(Long studentId, String name, String email)
    {
            Optional<Student> existingStudent = studentRepository.findById(studentId);

            if(existingStudent.isEmpty())
            {
                throw new IllegalStateException("student with id " + studentId + " does not exist");
            }
            else if(name != null && name.length() > 0 && !Objects.equals(existingStudent.get().getName(), name))
            {
                existingStudent.get().setName(name);
            }
            else if(email != null && email.length() > 0 && !Objects.equals(existingStudent.get().getEmail(), email))
            {
                Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
                if(studentOptional.isPresent())
                {
                    throw new IllegalStateException("email taken");
                }
                else
                {
                    existingStudent.get().setEmail(email);
                }
            }
    }
}
