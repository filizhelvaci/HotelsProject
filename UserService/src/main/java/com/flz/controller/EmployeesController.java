package com.flz.controller;

import com.flz.constant.EndPoint;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.dto.request.DoLoginRequestDto;
import com.flz.dto.response.DoRegisterResponseEmployeesDto;
import com.flz.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final UsersService usersService;

    @PostMapping(EndPoint.REGISTER)
    public ResponseEntity<DoRegisterResponseEmployeesDto> doRegister(@RequestBody DoEmployeeRegisterRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doRegisterEmployee(dto));
    }

    @PostMapping(EndPoint.LOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto){
        System.out.println("DTO: " +  dto);
        return ResponseEntity.ok(usersService.doLoginEmployee(dto));
    }

}
