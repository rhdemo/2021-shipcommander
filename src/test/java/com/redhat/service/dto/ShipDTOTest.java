package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class ShipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipDTO.class);
        ShipDTO shipDTO1 = new ShipDTO();
        shipDTO1.id = 1L;
        ShipDTO shipDTO2 = new ShipDTO();
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
        shipDTO2.id = shipDTO1.id;
        assertThat(shipDTO1).isEqualTo(shipDTO2);
        shipDTO2.id = 2L;
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
        shipDTO1.id = null;
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
    }
}
