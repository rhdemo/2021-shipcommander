package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class ContainerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerDTO.class);
        ContainerDTO containerDTO1 = new ContainerDTO();
        containerDTO1.id = 1L;
        ContainerDTO containerDTO2 = new ContainerDTO();
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO2.id = containerDTO1.id;
        assertThat(containerDTO1).isEqualTo(containerDTO2);
        containerDTO2.id = 2L;
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO1.id = null;
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
    }
}
