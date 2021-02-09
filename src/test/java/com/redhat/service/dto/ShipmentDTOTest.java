package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class ShipmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentDTO.class);
        ShipmentDTO shipmentDTO1 = new ShipmentDTO();
        shipmentDTO1.id = 1L;
        ShipmentDTO shipmentDTO2 = new ShipmentDTO();
        assertThat(shipmentDTO1).isNotEqualTo(shipmentDTO2);
        shipmentDTO2.id = shipmentDTO1.id;
        assertThat(shipmentDTO1).isEqualTo(shipmentDTO2);
        shipmentDTO2.id = 2L;
        assertThat(shipmentDTO1).isNotEqualTo(shipmentDTO2);
        shipmentDTO1.id = null;
        assertThat(shipmentDTO1).isNotEqualTo(shipmentDTO2);
    }
}
