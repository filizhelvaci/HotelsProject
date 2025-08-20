package com.flz.service.impl;

import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.PositionAlreadyDeletedException;
import com.flz.exception.PositionAlreadyExistsException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class PositionWriteServiceImpl implements PositionWriteService {

    private final PositionSavePort positionSavePort;
    private final PositionReadPort positionReadPort;
    private final DepartmentReadPort departmentReadPort;

    private static final PositionCreateRequestToPositionDomainMapper
            positionCreateRequestToPositionDomainMapper = PositionCreateRequestToPositionDomainMapper.INSTANCE;


    @Override
    public void create(PositionCreateRequest createRequest) {

        checkIfPositionNameExists(createRequest.getName());

        Position position = positionCreateRequestToPositionDomainMapper.map(createRequest);

        Long departmentId = createRequest.getDepartmentId();
        Department department = departmentReadPort.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        if (department.getStatus() == DepartmentStatus.DELETED) {
            department.setStatus(DepartmentStatus.ACTIVE);
        }

        position.setDepartment(department);
        position.setStatus(PositionStatus.ACTIVE);
        positionSavePort.save(position);
    }


    @Override
    public void update(Long id, PositionUpdateRequest positionUpdateRequest) {

        Position position = positionReadPort.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));

        String requestName = positionUpdateRequest.getName();
        Long requestDepartmentId = positionUpdateRequest.getDepartmentId();

        Department department = departmentReadPort.findById(requestDepartmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(requestDepartmentId));

        boolean isDuplicatePosition = position.getName()
                .equals(requestName) && position.getDepartment()
                .getId()
                .equals(requestDepartmentId);

        if (isDuplicatePosition) {
            throw new PositionAlreadyExistsException(requestName);
        }

        position.setName(requestName);
        position.setDepartment(department);
        position.setStatus(PositionStatus.ACTIVE);
        position.setUpdatedAt(LocalDateTime.now());
        position.setUpdatedBy("SYSTEM");

        positionSavePort.save(position);
    }

    private void checkIfPositionNameExists(String positionUpdateRequest) {

        boolean existsByName = positionReadPort
                .existsByName(positionUpdateRequest);
        if (existsByName) {
            throw new PositionAlreadyExistsException(positionUpdateRequest);
        }
    }


    @Override
    public void delete(Long id) {

        Position position = positionReadPort.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));

        if (position.isDeleted()) {
            throw new PositionAlreadyDeletedException(position.getId());
        }

        position.delete();
        positionSavePort.save(position);
    }

}
