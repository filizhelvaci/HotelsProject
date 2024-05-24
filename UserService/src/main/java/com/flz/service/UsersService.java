package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.Users;
import com.flz.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    IUsersRepository iUsersRepository;

    CustomersService customersService;



    public List<Users> getAllUsers() {
        return iUsersRepository.findAll();
    }

    public ResponseEntity<Users> getByUser(Long id)throws ResourceNotFoundException {

        Users users=iUsersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found ID: "+ id));

        return ResponseEntity.ok().body(users);
    }



    public Users saveUser(Users users){
        return iUsersRepository.save(users);
    }

    public String deleteUser(Long id){
        iUsersRepository.deleteById(id);
        return "user = "+id+ "deleted ";
    }

    public Users updateUsers(Users users){
        return iUsersRepository.save(users);
    }

/*

 /*
 getAllStudent
 getOneStudent
 createStudent
 deleteOneStudent
 updateOneStudent


    public Student createStudent(Student student) {
        // FIXME id değeri kontrolü yapılacak.

        if(studentRepository.findById(student.getId()).isPresent())
            return null;

       /*
        if(studentRepository.findById(student.getId()).isPresent()) {
           Student studentInfo = new Student();
            studentInfo.setFirstName(student.getFirstName());
            studentInfo.setLastName(student.getLastName());
            studentInfo.setEmail(student.getEmail());
           return studentRepository.save(studentInfo);
       }

        return studentRepository.save(student);
    }


    public Map<String, Boolean> deleteOneStudent (Long id) throws ResourceNotFoundException {

        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found ID: " + id));

        studentRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete", Boolean.TRUE);

        return  response;
    }



    public ResponseEntity<Student> updateOneStudent (Student studentInfo) throws ResourceNotFoundException {

        Student student = studentRepository.findById(studentInfo.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Student not found ID: " + studentInfo.getId()));

        return ResponseEntity.ok(studentRepository.save(studentInfo));
    }

 ---------------------------------------- Update için burayı kullnamak daha mantıklı
    public ResponseEntity<Student> updateOneStudent2 (Long id, Student studentInfo) throws ResourceNotFoundException {

        studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found ID: " + id));

        studentInfo.setId(id);
        return ResponseEntity.ok(studentRepository.save(studentInfo));
    }
 */
}
