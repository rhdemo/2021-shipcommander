package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class SensorReadingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorReadingsDTO.class);
        SensorReadingsDTO sensorReadingsDTO1 = new SensorReadingsDTO();
        sensorReadingsDTO1.id = 1L;
        SensorReadingsDTO sensorReadingsDTO2 = new SensorReadingsDTO();
        assertThat(sensorReadingsDTO1).isNotEqualTo(sensorReadingsDTO2);
        sensorReadingsDTO2.id = sensorReadingsDTO1.id;
        assertThat(sensorReadingsDTO1).isEqualTo(sensorReadingsDTO2);
        sensorReadingsDTO2.id = 2L;
        assertThat(sensorReadingsDTO1).isNotEqualTo(sensorReadingsDTO2);
        sensorReadingsDTO1.id = null;
        assertThat(sensorReadingsDTO1).isNotEqualTo(sensorReadingsDTO2);
    }
}
