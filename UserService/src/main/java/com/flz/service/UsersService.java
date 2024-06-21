package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.request.DoRegisterRequestDto;
import com.flz.dto.response.DoRegisterResponseDto;
import com.flz.exception.ErrorType;
import com.flz.exception.ResourceNotFoundException;
import com.flz.exception.UserServiceException;
import com.flz.model.Customers;
import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.ICustomersRepository;
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

    // ****************** @AutoWired *************** //
    private final IUsersRepository IusersRepository;

    public UsersService(IUsersRepository IusersRepository) {
        super(IusersRepository);
        this.IusersRepository = IusersRepository;
    }
   // ************************************************* //

    @Autowired
    private EmployeesService employeesService;


    @Autowired
    private CustomersService customersService;


    public DoRegisterResponseDto doRegisterCustomer(DoCustomerRegisterRequestDto dto) {

        System.out.println("DoCustomerRegisterRequestDto: " + dto);

       if (IusersRepository.existsByEmail(dto.getEmail()))
            throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);

        Users users = new Users();
        if (dto.getId() != null) {
            users = IusersRepository.findById(dto.getId()).orElse(new Users());
        }

        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new UserServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);

        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setCreateAt(System.currentTimeMillis());
        users.setState(true);


        Customers customers=customersService.saveCustomer(dto);
        users.setCustomer(customers);

        Users savedUser = IusersRepository.save(users);
        DoRegisterResponseDto doRegisterResponseDto=new DoRegisterResponseDto();
        doRegisterResponseDto.setPassword(savedUser.getPassword());
        doRegisterResponseDto.setEmail(savedUser.getEmail());

        //Customers savedCustomer=IcustomersRepository.save(customers);

        return doRegisterResponseDto;
    }
//
//    UserProfile userProfile = userProfileService.saveUserProfile(userDTO);
//    UserAddress userAddress = userAddressService.saveUserAddress(userDTO);
//
//        user.setUserProfile(userProfile);
//        user.setUserAddress(userAddress);
//
//    User savedUser = userRepository.save(user);
//
//        return convertToDTO(savedUser);
//}
//
//private UserDTO convertToDTO(User user) {
//    UserDTO userDTO = new UserDTO();
//    userDTO.setId(user.getId());
//    userDTO.setName(user.getName());
//
//    if (user.getUserProfile != null) {
//        userDTO.setPhoneNumber(user.getUserProfile().getPhoneNumber());
//    }
//    if (user.getUserAddress() != null) {
//        userDTO.setAddress(user.getUserAddress().getAddress());
//    }
//
//    return userDTO;
//}

//      private DoCustomerRegisterRequestDto  convertToDTO(Users users)
//      {
//            DoCustomerRegisterRequestDto dto = new DoCustomerRegisterRequestDto();
//            dto.setId(users.getId());
//            dto.setName(users.getName());
//            dto.setLastName(users.getLastName());
//            dto.setPassword(users.getPassword());
//            dto.setEmail(users.getEmail());
//            dto.setPhoneNumber(users.getPhoneNumber());
//
//            if (users.getCustomer()!= null) {
//                dto.setBirthDate(users.getCustomer().getBirthDate());
//                dto.setNationality(users.getCustomer().getNationality());
//                dto.setIDnumber(users.getCustomer().getIDnumber());
//            }
//            return dto;
//
//    }

    public DoRegisterResponseDto doRegisterEmployee(DoEmployeeRegisterRequestDto dto) {

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

//        Employees employees=new Employees();
//        employees.setBirthDate(dto.getBirthDate());
//        employees.setIDnumber(dto.getIDnumber());
//        employees.setInsideNumber(dto.getInsideNumber());
//        employees.setContractPeriod(dto.getContractPeriod());
//        employees.setGraduationStatus(dto.getGraduationStatus());
////        employees.setUser(users);

      //  IemployeesRepository.save(employees);

      //  System.out.println("Employee : "+ employees);
//        System.out.println("User : "+ users);

        DoRegisterResponseDto responseDto = new DoRegisterResponseDto();
        responseDto.setEmail(dto.getEmail());
        responseDto.setEmail(dto.getPassword());
        return responseDto;

    }

    public String doLoginCustomer(DoLoginRequestDto dto) {

        Optional<Users> users = IusersRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (users.isEmpty())
            throw new UserServiceException(ErrorType.DOLOGIN_USERNAMEORPASSWORD_NOTEXISTS);

        return users.get().getId().toString();
    }

    public String doLoginEmployee(DoLoginRequestDto dto) {

        Optional<Users> users = IusersRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (users.isEmpty())
            throw new UserServiceException(ErrorType.DOLOGIN_USERNAMEORPASSWORD_NOTEXISTS);

        return users.get().getId().toString();
    }

//8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888//
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
