package com.flz.service.impl;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.service.RoomTypeService;
import com.flz.service.impl.com.flz.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class RoomServiceImplTest extends BaseTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomTypeService roomTypeService;

    @InjectMocks
    RoomServiceImpl roomService;

    /**
     * findSummaryAll()
     */
    @Test
    public void whenCalledAllSummaryRooms_thenReturnListOfRoomsSummaryResponse() {

        //When
        List<AssetEntity> mockAssets = List.of(
                AssetEntity.builder().id(1L).name("Yatak Seti").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(2L).name("55 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(3L).name("Wifi").price(BigDecimal.valueOf(0)).isDefault(false).build(),
                AssetEntity.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );
        RoomTypeEntity mockRoomTypeEntity1 = RoomTypeEntity.builder()
                .id(1L)
                .name("Standart Oda")
                .price(BigDecimal.valueOf(2000))
                .size(30)
                .personCount(2)
                .description("Bahçe manzaralı bu oda standart şekilde tasarlanmıştır ve bir yatak odası bulunmaktadır. " +
                        "Bir kanepe/koltuk bulunur ve tuvaleti olan bir banyo sunmaktadır. " +
                        "Ayrıca WiFi, 55 inç LCD TV, MP3 çalar/radyo/çalar saat, çay/kahve yapma olanaklarını " +
                        "ve WiFi erişimini kapsamaktadır.")
                .assets(mockAssets)
                .build();

        List<RoomEntity> mockRoomEntities = List.of(
                RoomEntity.builder()
                        .id(10L)
                        .number(201)
                        .floor(2)
                        .status(RoomStatus.EMPTY)
                        .type(mockRoomTypeEntity1)
                        .build(),
                RoomEntity.builder()
                        .id(10L)
                        .number(201)
                        .floor(2)
                        .status(RoomStatus.EMPTY)
                        .type(mockRoomTypeEntity1)
                        .build(),
                RoomEntity.builder()
                        .id(10L)
                        .number(201)
                        .floor(2)
                        .status(RoomStatus.EMPTY)
                        .type(mockRoomTypeEntity1)
                        .build()
        );

        Mockito.when(roomRepository.findAll())
                .thenReturn(mockRoomEntities);
        List<RoomsSummaryResponse> mockRoomsSummaryResponses =
                RoomEntityToSummaryResponseMapper.INSTANCE.map(mockRoomEntities);
        List<RoomsSummaryResponse> result = roomService.findSummaryAll();

        //Then
        Assertions.assertNotNull(mockRoomsSummaryResponses);
        Assertions.assertEquals(3, result.size());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();

    }

    /**
     * findById
     */

    /**
     * findById-Exception
     */

    /**
     * findAll
     */

    /**
     * create
     */

    /**
     * create-exception
     */

    /**
     * update
     */

    /**
     * update-exception
     */

    /**
     * delete
     */
    /**
     * delete-exception
     */

}

