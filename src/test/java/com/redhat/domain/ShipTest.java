package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class ShipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ship.class);
        Ship ship1 = new Ship();
        ship1.id = 1L;
        Ship ship2 = new Ship();
        ship2.id = ship1.id;
        assertThat(ship1).isEqualTo(ship2);
        ship2.id = 2L;
        assertThat(ship1).isNotEqualTo(ship2);
        ship1.id = null;
        assertThat(ship1).isNotEqualTo(ship2);
    }
}
