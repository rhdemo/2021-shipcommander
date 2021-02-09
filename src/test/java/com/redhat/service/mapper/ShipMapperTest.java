package com.redhat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipMapperTest {

    private ShipMapper shipMapper;

    @BeforeEach
    public void setUp() {
        shipMapper = new ShipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shipMapper.fromId(id).id).isEqualTo(id);
        assertThat(shipMapper.fromId(null)).isNull();
    }
}
