package com.flz.port;

import com.flz.model.Position;

import java.util.Optional;

public interface PositionTestPort {

    Position save(Position position);

    Optional<Position> findByName(String positionName);

}
