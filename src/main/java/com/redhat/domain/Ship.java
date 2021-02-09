package com.redhat.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.redhat.domain.enumeration.ShipType;

/**
 * A Ship.
 */
@Entity
@Table(name = "ship")
@Cacheable
@RegisterForReflection
public class Ship extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Column(name = "name")
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "vessel_type")
    public ShipType vesselType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        return id != null && id.equals(((Ship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ship{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", vesselType='" + vesselType + "'" +
            "}";
    }

    public Ship update() {
        return update(this);
    }

    public Ship persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Ship update(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("ship can't be null");
        }
        var entity = Ship.<Ship>findById(ship.id);
        if (entity != null) {
            entity.name = ship.name;
            entity.vesselType = ship.vesselType;
        }
        return entity;
    }

    public static Ship persistOrUpdate(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("ship can't be null");
        }
        if (ship.id == null) {
            persist(ship);
            return ship;
        } else {
            return update(ship);
        }
    }


}
