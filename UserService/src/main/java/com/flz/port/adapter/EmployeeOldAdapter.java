package com.flz.port.adapter;

import com.flz.model.EmployeeOld;
import com.flz.model.entity.EmployeeOldEntity;
import com.flz.model.mapper.EmployeeOldEntityToDomainMapper;
import com.flz.model.mapper.EmployeeOldToEntityMapper;
import com.flz.port.EmployeeOldReadPort;
import com.flz.port.EmployeeOldSavePort;
import com.flz.repository.EmployeeOldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeOldAdapter implements EmployeeOldReadPort, EmployeeOldSavePort {

    private final EmployeeOldRepository employeeOldRepository;

    private final EmployeeOldEntityToDomainMapper employeeOldEntityToDomainMapper = EmployeeOldEntityToDomainMapper.INSTANCE;
    private final EmployeeOldToEntityMapper employeeOldToEntityMapper = EmployeeOldToEntityMapper.INSTANCE;


    @Override
    public Optional<EmployeeOld> findById(Long id) {
        Optional<EmployeeOldEntity> employeeOldEntity = employeeOldRepository.findById(id);
        return employeeOldEntity.map(employeeOldEntityToDomainMapper::map);
    }

    @Override
    public List<EmployeeOld> findAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<EmployeeOldEntity> employeeOldEntities = employeeOldRepository
                .findAll(pageable)
                .getContent();
        return employeeOldEntities.stream()
                .map(employeeOldEntityToDomainMapper::map)
                .toList();
    }

    @Override
    public EmployeeOld save(final EmployeeOld employeeOld) {
        final EmployeeOldEntity employeeOldEntity = employeeOldToEntityMapper.map(employeeOld);
        return employeeOldEntityToDomainMapper
                .map(employeeOldRepository.save(employeeOldEntity));
    }

}
