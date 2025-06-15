package com.flz.model.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class PositionSummaryResponse {

    private Long id;
    private String name;
}
