package com.redhat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.redhat.TestUtil;

public class CompanyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDTO.class);
        CompanyDTO companyDTO1 = new CompanyDTO();
        companyDTO1.id = 1L;
        CompanyDTO companyDTO2 = new CompanyDTO();
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
        companyDTO2.id = companyDTO1.id;
        assertThat(companyDTO1).isEqualTo(companyDTO2);
        companyDTO2.id = 2L;
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
        companyDTO1.id = null;
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
    }
}
