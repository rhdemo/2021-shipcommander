package com.redhat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentMapperTest {

    private ShipmentMapper shipmentMapper;

    @BeforeEach
    public void setUp() {
        shipmentMapper = new ShipmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shipmentMapper.fromId(id).id).isEqualTo(id);
        assertThat(shipmentMapper.fromId(null)).isNull();
    }
}
