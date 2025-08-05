package com.flz.port;

import com.flz.model.Position;

public interface PositionTestPort {

    Position save(Position position);

    Position findByName(String positionName);

}
