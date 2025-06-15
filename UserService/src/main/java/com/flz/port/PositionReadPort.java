package com.flz.port;

import com.flz.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionReadPort {

    List<Position> findAll(Integer page, Integer pageSize);

    List<Position> findSummaryAll();

    Optional<Position> findById(Long id);

    boolean existsByName(String name);

}
