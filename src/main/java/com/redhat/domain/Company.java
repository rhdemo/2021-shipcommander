package com.redhat.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cacheable
@RegisterForReflection
public class Company extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "address_line")
    public String addressLine;

    @Column(name = "city")
    public String city;

    @Column(name = "country")
    public String country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", addressLine='" + addressLine + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            "}";
    }

    public Company update() {
        return update(this);
    }

    public Company persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Company update(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("company can't be null");
        }
        var entity = Company.<Company>findById(company.id);
        if (entity != null) {
            entity.name = company.name;
            entity.addressLine = company.addressLine;
            entity.city = company.city;
            entity.country = company.country;
        }
        return entity;
    }

    public static Company persistOrUpdate(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("company can't be null");
        }
        if (company.id == null) {
            persist(company);
            return company;
        } else {
            return update(company);
        }
    }


}
