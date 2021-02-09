package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class ContainerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Container.class);
        Container container1 = new Container();
        container1.id = 1L;
        Container container2 = new Container();
        container2.id = container1.id;
        assertThat(container1).isEqualTo(container2);
        container2.id = 2L;
        assertThat(container1).isNotEqualTo(container2);
        container1.id = null;
        assertThat(container1).isNotEqualTo(container2);
    }
}
