package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.request.DoRegisterRequestDto;
import com.flz.exception.ErrorType;
import com.flz.exception.ResourceNotFoundException;
import com.flz.exception.UserServiceException;
import com.flz.model.Customers;
import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.IEmployeesRepository;
import com.flz.repository.IUsersRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class UsersService extends ServiceManager<Users,Long> {

    private final IUsersRepository IusersRepository;

    public UsersService(IUsersRepository IusersRepository) {
        super(IusersRepository);
        this.IusersRepository = IusersRepository;
    }


    CustomersService customersService;

    EmployeesService employeesService;

    public Users doRegisterCustomer(DoCustomerRegisterRequestDto dto) {

        System.out.println("DoCustomerRegisterRequestDto: "+dto);

        //if (repository.existsByUsername(dto.getUsername()))
        //   throw new Exception();

        Users users=new Users();

        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new UserServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);

        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setCreateAt(System.currentTimeMillis());
        users.setState(true);
        save(users);

        Customers customers=new Customers();
        customers.setBirthDate(dto.getBirthDate());
        customers.setNationality(dto.getNationality());
        customers.setIDnumber(dto.getIDnumber());
        customers.setUser(users);
        customersService.saveCustomer(customers);

        System.out.println("Customer : "+ customers);
        System.out.println("User : "+ users);

        return users;
    }

    public Users doRegisterEmployee(DoEmployeeRegisterRequestDto dto) {

        System.out.println("DoEmployeeRegisterRequestDto: "+dto);

        if (IusersRepository.existsByEmail(dto.getEmail()))
                throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);



        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new UserServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);

        Users users=new Users();
        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setCreateAt(System.currentTimeMillis());
        users.setState(true);
        save(users);

        Employees employees=new Employees();
        employees.setBirthDate(dto.getBirthDate());
        employees.setIDnumber(dto.getIDnumber());
        employees.setInsideNumber(dto.getInsideNumber());
        employees.setContractPeriod(dto.getContractPeriod());
        employees.setGraduationStatus(dto.getGraduationStatus());
        employees.setUser(users);


        employeesService.saveEmployee(employees);

        System.out.println("Employee : "+ employees);
        System.out.println("User : "+ users);

        return users;
    }
//    // JWTsiz sıradan düz bir login
//    public String doLoginOld(DoLoginRequestDto dto) {
//
//        Optional<Users> users = IusersRepository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
//        if(customersService.getByCustomer(users.get().getId()))
//            return "Aradığınız kayıt bilgileri: "+
//        if (users.isEmpty())
//            throw new UserServiceException(ErrorType.DOLOGIN_USERNAMEORPASSWORD_NOTEXISTS);
//
//        return users.get().getId().toString();
//    }
//


//    public List<Users> getAllUsers() {
//        return IusersRepository.findAll();
//    }
//
//    public ResponseEntity<Users> getByUser(Long id)throws ResourceNotFoundException {
//
//        Users users=IusersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found ID: "+ id));
//
//        return ResponseEntity.ok().body(users);
//    }
//
//
//    public Users saveUser(Users users){
//
//        if(IusersRepository.findById(users.getId()).isPresent())
//            return null;
//
//        return IusersRepository.save(users);
//    }
//
//    public Map<String,Boolean> deleteUser(Long id) throws ResourceNotFoundException{
//
//        Users users=IusersRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("Customer not found ID : " +id));
//
//        IusersRepository.deleteById(id);
//
//        Map<String,Boolean> response=new HashMap<>();
//        response.put("Deleted "+id ,Boolean.TRUE);
//
//        return response;
//    }
//
//    public ResponseEntity<Users> updateUsers(Long id,Users users)throws ResourceNotFoundException{
//
//        IusersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException( "User not found ID : "+id));
//        users.setId(id);
//        return ResponseEntity.ok(IusersRepository.save(users));
//
//    }

}
