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
 * A Port.
 */
@Entity
@Table(name = "port")
@Cacheable
@RegisterForReflection
public class Port extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longtitude")
    public Double longtitude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Port)) {
            return false;
        }
        return id != null && id.equals(((Port) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Port{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", latitude=" + latitude +
            ", longtitude=" + longtitude +
            "}";
    }

    public Port update() {
        return update(this);
    }

    public Port persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Port update(Port port) {
        if (port == null) {
            throw new IllegalArgumentException("port can't be null");
        }
        var entity = Port.<Port>findById(port.id);
        if (entity != null) {
            entity.name = port.name;
            entity.latitude = port.latitude;
            entity.longtitude = port.longtitude;
        }
        return entity;
    }

    public static Port persistOrUpdate(Port port) {
        if (port == null) {
            throw new IllegalArgumentException("port can't be null");
        }
        if (port.id == null) {
            persist(port);
            return port;
        } else {
            return update(port);
        }
    }


}
