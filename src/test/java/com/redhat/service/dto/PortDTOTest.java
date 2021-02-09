package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class PortDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortDTO.class);
        PortDTO portDTO1 = new PortDTO();
        portDTO1.id = 1L;
        PortDTO portDTO2 = new PortDTO();
        assertThat(portDTO1).isNotEqualTo(portDTO2);
        portDTO2.id = portDTO1.id;
        assertThat(portDTO1).isEqualTo(portDTO2);
        portDTO2.id = 2L;
        assertThat(portDTO1).isNotEqualTo(portDTO2);
        portDTO1.id = null;
        assertThat(portDTO1).isNotEqualTo(portDTO2);
    }
}
