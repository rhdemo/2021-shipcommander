package com.redhat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.redhat.TestUtil;
import org.junit.jupiter.api.Test;


public class CompanyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.id = 1L;
        Company company2 = new Company();
        company2.id = company1.id;
        assertThat(company1).isEqualTo(company2);
        company2.id = 2L;
        assertThat(company1).isNotEqualTo(company2);
        company1.id = null;
        assertThat(company1).isNotEqualTo(company2);
    }
}
