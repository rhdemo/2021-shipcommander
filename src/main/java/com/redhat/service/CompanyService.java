package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.Company;
import com.redhat.service.dto.CompanyDTO;
import com.redhat.service.mapper.CompanyMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Inject
    CompanyMapper companyMapper;

    @Transactional
    public CompanyDTO persistOrUpdate(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        var company = companyMapper.toEntity(companyDTO);
        company = Company.persistOrUpdate(company);
        return companyMapper.toDto(company);
    }

    /**
     * Delete the Company by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        Company.findByIdOptional(id).ifPresent(company -> {
            company.delete();
        });
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CompanyDTO> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return Company.findByIdOptional(id)
            .map(company -> companyMapper.toDto((Company) company)); 
    }

    /**
     * Get all the companies.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<CompanyDTO> findAll(Page page) {
        log.debug("Request to get all Companies");
        return new Paged<>(Company.findAll().page(page))
            .map(company -> companyMapper.toDto((Company) company));
    }



}
