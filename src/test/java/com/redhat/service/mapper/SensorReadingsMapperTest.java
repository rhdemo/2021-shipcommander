package com.redhat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SensorReadingsMapperTest {

    private SensorReadingsMapper sensorReadingsMapper;

    @BeforeEach
    public void setUp() {
        sensorReadingsMapper = new SensorReadingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sensorReadingsMapper.fromId(id).id).isEqualTo(id);
        assertThat(sensorReadingsMapper.fromId(null)).isNull();
    }
}
