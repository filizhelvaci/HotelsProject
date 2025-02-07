package com.flz.service.impl;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import com.flz.repository.RoomRepository;
import com.flz.repository.RoomTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoomTypeAvailabilityServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomTypeRepository roomTypeRepository;

    @InjectMocks
    RoomTypeAvailabilityServiceImpl roomTypeAvailabilityServiceImpl;


    @Test
    public void whenTheCustomerWantsToSeeTheRooms_thenListRoomTypesAndAvailability() {

        //Given
        List<AssetEntity> assets = List.of(
                AssetEntity.builder().id(1L).name("Yatak Seti").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(2L).name("55 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(3L).name("Wifi").price(BigDecimal.valueOf(0)).isDefault(false).build(),
                AssetEntity.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );
        List<RoomTypeEntity> roomTypeEntities = List.of(RoomTypeEntity.builder()
                        .id(1L)
                        .name("Standart Oda")
                        .price(BigDecimal.valueOf(2000))
                        .size(30)
                        .personCount(2)
                        .description("Bahçe manzaralı bu oda standart şekilde tasarlanmıştır ve bir yatak odası bulunmaktadır. " +
                                "Bir kanepe/koltuk bulunur ve tuvaleti olan bir banyo sunmaktadır. " +
                                "Ayrıca WiFi, 55 inç LCD TV, MP3 çalar/radyo/çalar saat, çay/kahve yapma olanaklarını " +
                                "ve WiFi erişimini kapsamaktadır.")
                        .assets(assets)
                        .build(),
                RoomTypeEntity.builder()
                        .id(2L)
                        .name("Lux Oda")
                        .price(BigDecimal.valueOf(4000))
                        .size(50)
                        .personCount(3)
                        .description("Bahçe manzarası, balkon, küvet, duş ve ayrı tuvalet, 55-inç TV, WiFi, " +
                                "50 metrekarelik bu konforlu ve ferah bahçe manzaralı odada bir king yatak, balkon, " +
                                "kanepe veya koltuk, küvetli banyo, duş ve ayrı tuvalet bulunmaktadır. " +
                                "55-inç LCD televizyonda bir programın veya MP3 çalar radyo-çalar saatte müziğin keyfini çıkarın." +
                                "Diğer hizmetler arasında WiFi ve çay/kahve yapma olanakları yer almaktadır.")
                        .assets(assets)
                        .build()
        );

        List<RoomEntity> roomEntities = List.of(RoomEntity.builder()
                        .id(1L)
                        .number(101)
                        .floor(1)
                        .status(RoomStatus.EMPTY)
                        .type(roomTypeEntities.get(1))
                        .build(),
                RoomEntity.builder()
                        .id(2L)
                        .number(102)
                        .floor(1)
                        .status(RoomStatus.EMPTY)
                        .type(roomTypeEntities.get(0))
                        .build()
        );

        //When
        Mockito.when(roomRepository.findAll()).thenReturn(roomEntities);
        Mockito.when(roomTypeRepository.findAll()).thenReturn(roomTypeEntities);

        List<RoomTypeAvailabilityResponse> roomTypeAvailabilityResponses = roomTypeAvailabilityServiceImpl.findRoomTypesAvailability();

        //Then
        Assertions.assertNotNull(roomTypeAvailabilityResponses);
        Assertions.assertEquals(roomTypeEntities.size(), roomTypeAvailabilityResponses.size());
        Mockito.verify(roomTypeRepository, Mockito.times(1)).findAll();
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();

    }
}