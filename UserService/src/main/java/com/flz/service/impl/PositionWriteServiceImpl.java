package com.flz.service.impl;

import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.PositionAlreadyExistsException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionCreateRequestToPositionDomainMapper;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.PositionReadPort;
import com.flz.port.PositionSavePort;
import com.flz.service.PositionWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PositionWriteServiceImpl implements PositionWriteService {

    private final PositionSavePort positionSavePort;
    private final PositionReadPort positionReadPort;
    private final DepartmentReadPort departmentReadPort;

    private final PositionCreateRequestToPositionDomainMapper
            positionCreateRequestToPositionDomainMapper = PositionCreateRequestToPositionDomainMapper.INSTANCE;


    @Override
    public void create(PositionCreateRequest createRequest) {

        boolean existsByName = positionReadPort.existsByName(createRequest.getName());
        if (existsByName) {
            throw new PositionAlreadyExistsException(createRequest.getName());
        }

        Position position = positionCreateRequestToPositionDomainMapper.map(createRequest);

        Long typeId = createRequest.getDepartmentId();
        Department department = departmentReadPort.findById(typeId)
                .orElseThrow(() -> new DepartmentNotFoundException(typeId));
        position.setDepartment(department);
        position.setStatus(PositionStatus.ACTIVE);
        positionSavePort.save(position);

    }


    @Override
    public void update(Long id, PositionUpdateRequest positionUpdateRequest) {

        Position position = positionReadPort.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));

        boolean existsByName = positionReadPort
                .existsByName(positionUpdateRequest.getName());
        if (existsByName) {
            throw new PositionAlreadyExistsException(positionUpdateRequest.getName());
        }

        Long typeId = positionUpdateRequest.getDepartmentId();
        Department department = departmentReadPort.findById(typeId)
                .orElseThrow(() -> new DepartmentNotFoundException(typeId));

        position.setName(positionUpdateRequest.getName());
        position.setDepartment(department);
        position.setStatus(PositionStatus.ACTIVE);
        positionSavePort.save(position);
    }


    @Override
    public void delete(Long id) {

        Position position = positionReadPort.findById(id).orElseThrow(() -> new PositionNotFoundException(id));

        position.setStatus(PositionStatus.DELETED);
        positionSavePort.save(position);
    }


}
