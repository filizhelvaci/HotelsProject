package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseCustomerDto;
import com.flz.dto.response.DoRegisterResponseEmployeesDto;
import com.flz.exception.ErrorType;
import com.flz.exception.UserServiceException;
import com.flz.model.Customers;
import com.flz.model.Employees;
import com.flz.model.Users;
import com.flz.repository.IUsersRepository;
import com.flz.utils.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public DoRegisterResponseCustomerDto doRegisterCustomer(DoCustomerRegisterRequestDto dto) {

        Users users = new Users();

//       if (IusersRepository.existsByEmail(dto.getEmail()))
//            throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);

       if (dto.getEmail() != null) {
            users = IusersRepository.findByEmail(dto.getEmail()).orElse(new Users());
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

//
//        //FIXME çalışmazsa buraya converttoDTO eklenecek
//        DoRegisterResponseDto doRegisterResponseDto=new DoRegisterResponseDto();
//        doRegisterResponseDto.setPassword(savedUser.getPassword());
//        doRegisterResponseDto.setEmail(savedUser.getEmail());
//
//        //Customers savedCustomer=IcustomersRepository.save(customers);
//
//        return doRegisterResponseDto;

        return convertToDTO(savedUser);
    }
    private DoRegisterResponseCustomerDto convertToDTO(Users user) {
        DoRegisterResponseCustomerDto responseDto = new DoRegisterResponseCustomerDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setLastname(user.getLastName());


        if (user.getCustomer()!= null) {
            responseDto.setIDnumber(user.getCustomer().getIDnumber());
        }

        return responseDto;
}


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

    public DoRegisterResponseEmployeesDto doRegisterEmployee(DoEmployeeRegisterRequestDto dto) {

        //        if (IusersRepository.existsByEmail(dto.getEmail()))
//                throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);

        Users users = new Users();

        if (dto.getEmail() != null) {
            users = IusersRepository.findByEmail(dto.getEmail()).orElse(new Users());
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

        Employees employees=employeesService.saveEmployee(dto);
        users.setEmployee(employees);


//        //FIXME Convert can do
//        DoRegisterResponseCustomerDto responseDto = new DoRegisterResponseCustomerDto();
//        responseDto.setEmail(dto.getEmail());
//        responseDto.setEmail(dto.getPassword());
//        return responseDto;
        Users savedUser = IusersRepository.save(users);

        return convertToEDTO(savedUser);

    }
    private DoRegisterResponseEmployeesDto convertToEDTO(Users user) {
        DoRegisterResponseEmployeesDto responseDto = new DoRegisterResponseEmployeesDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setLastname(user.getLastName());
        responseDto.setPassword(user.getPassword());


        if (user.getCustomer()!= null) {
            responseDto.setId(user.getCustomer().getId());
        }

        return responseDto;
    }

    public String doLoginCustomer(DoLoginRequestDto dto) {

        Optional<Users> users = IusersRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (users.isEmpty())
            throw new UserServiceException(ErrorType.DOLOGIN_USERNAMEORPASSWORD_NOTEXISTS);

        return users.get().getEmail().toString();
    }

    public String doLoginEmployee(DoLoginRequestDto dto) {

        Optional<Users> users = IusersRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (users.isEmpty())
            throw new UserServiceException(ErrorType.DOLOGIN_USERNAMEORPASSWORD_NOTEXISTS);

        return users.get().getEmail().toString();
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
