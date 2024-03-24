package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @GetMapping//this annotation tells the app what to do when it gets a get request
    public List<Student> getStudents()
    {
        return studentService.getStudents();
    }


    @PostMapping//this annotation tells the app what to do when it gets a post request
    public void registerNewStudent(@RequestBody Student student)//this annotation maps the request body contents onto a student object
    {
        studentService.addNewStudent(student);
    }


    //the "pathvariable is referring to the content that comes after the final '/' in the URI path
    //In this case if the DELETE request is sent to http://localhost:8080/api/v1/student/1 then it will pass '1' as the pathvariable
    @DeleteMapping(path = "{studentId}") //this annotation tells the app what to do when it gets a delete request
    public void deleteStudent(@PathVariable("studentId") Long studentId)
    {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path  = "{studentId}") //this annotation tells the app what to do when it gets a put request
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentId, name, email);
    }
}
