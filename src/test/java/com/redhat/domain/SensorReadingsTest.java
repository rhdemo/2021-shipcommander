package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class SensorReadingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorReadings.class);
        SensorReadings sensorReadings1 = new SensorReadings();
        sensorReadings1.id = 1L;
        SensorReadings sensorReadings2 = new SensorReadings();
        sensorReadings2.id = sensorReadings1.id;
        assertThat(sensorReadings1).isEqualTo(sensorReadings2);
        sensorReadings2.id = 2L;
        assertThat(sensorReadings1).isNotEqualTo(sensorReadings2);
        sensorReadings1.id = null;
        assertThat(sensorReadings1).isNotEqualTo(sensorReadings2);
    }
}
