package com.redhat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PortMapperTest {

    private PortMapper portMapper;

    @BeforeEach
    public void setUp() {
        portMapper = new PortMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(portMapper.fromId(id).id).isEqualTo(id);
        assertThat(portMapper.fromId(null)).isNull();
    }
}
