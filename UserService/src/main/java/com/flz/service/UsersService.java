package com.flz.service;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseCustomerDto;
import com.flz.dto.response.DoRegisterResponseEmployeesDto;
import com.flz.exception.ErrorType;
import com.flz.exception.UserServiceException;
import com.flz.mapper.IUsersMapper;
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
    EmployeesService employeesService;


    @Autowired
    CustomersService customersService;



    public DoRegisterResponseCustomerDto doRegisterCustomer(DoCustomerRegisterRequestDto dto) {

       if (IusersRepository.existsByEmail(dto.getEmail()))
            throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);

        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new UserServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);

        Users user= IUsersMapper.INSTANCE.toUserC(dto);

        Customers customers=customersService.saveCustomer(dto);

        Users savedUser = IusersRepository.save(user);

        DoRegisterResponseCustomerDto doRegisterResponseDto=new DoRegisterResponseCustomerDto();
        doRegisterResponseDto.setName(savedUser.getName());
        doRegisterResponseDto.setLastname(savedUser.getLastName());
        doRegisterResponseDto.setEmail(savedUser.getEmail());
        doRegisterResponseDto.setIDnumber(customers.getIDnumber());
        doRegisterResponseDto.setCustomerId(customers.getId());

        return doRegisterResponseDto;
    }

    public DoRegisterResponseEmployeesDto doRegisterEmployee(DoEmployeeRegisterRequestDto dto) {

        if (IusersRepository.existsByEmail(dto.getEmail()))
          throw new UserServiceException(ErrorType.KAYIT_EKLEME_HATASI);

        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new UserServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);

        Users user= IUsersMapper.INSTANCE.toUserE(dto);

        Employees employees=employeesService.saveEmployee(dto);

        Users savedUser = IusersRepository.save(user);

        DoRegisterResponseEmployeesDto responseDto = new DoRegisterResponseEmployeesDto();
        responseDto.setEmail(savedUser.getEmail());
        responseDto.setName(savedUser.getName());
        responseDto.setLastname(savedUser.getLastName());
        responseDto.setPassword(savedUser.getPassword());
        responseDto.setEmployeeId(employees.getId());

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

}
