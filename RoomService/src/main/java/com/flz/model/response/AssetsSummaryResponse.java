package com.flz.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AssetsSummaryResponse {

    private Long id;
    private String name;
}
