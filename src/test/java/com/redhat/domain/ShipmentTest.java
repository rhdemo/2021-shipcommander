package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class ShipmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipment.class);
        Shipment shipment1 = new Shipment();
        shipment1.id = 1L;
        Shipment shipment2 = new Shipment();
        shipment2.id = shipment1.id;
        assertThat(shipment1).isEqualTo(shipment2);
        shipment2.id = 2L;
        assertThat(shipment1).isNotEqualTo(shipment2);
        shipment1.id = null;
        assertThat(shipment1).isNotEqualTo(shipment2);
    }
}
