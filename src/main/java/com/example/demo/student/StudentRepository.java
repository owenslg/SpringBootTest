package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> { //Long is the type of the ID in the Student class


    //the below runs the SQL: SELECT * FROM student WHERE email = 'the email we are trying to add'
    //Optional<Student> findStudentByEmail(String email);

    //OR do it this way
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
