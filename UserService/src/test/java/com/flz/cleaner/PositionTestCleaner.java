package com.flz.cleaner;

import com.flz.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class PositionTestCleaner {

    private final PositionRepository positionRepository;

    public PositionTestCleaner(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Transactional
    public void cleanTestPositions() {
        positionRepository.deleteAllByNameContainingIgnoreCase("test");
    }

}
