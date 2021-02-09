package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class PortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Port.class);
        Port port1 = new Port();
        port1.id = 1L;
        Port port2 = new Port();
        port2.id = port1.id;
        assertThat(port1).isEqualTo(port2);
        port2.id = 2L;
        assertThat(port1).isNotEqualTo(port2);
        port1.id = null;
        assertThat(port1).isNotEqualTo(port2);
    }
}
