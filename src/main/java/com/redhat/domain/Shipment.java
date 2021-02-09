package com.redhat.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cacheable
@RegisterForReflection
public class Shipment extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @OneToOne
    @JoinColumn(unique = true)
    public Port startPort;

    @OneToOne
    @JoinColumn(unique = true)
    public Port endPort;

    @OneToOne
    @JoinColumn(unique = true)
    public Ship ship;

    @OneToMany(mappedBy = "shipment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<Container> containers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipment)) {
            return false;
        }
        return id != null && id.equals(((Shipment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + id +
            "}";
    }

    public Shipment update() {
        return update(this);
    }

    public Shipment persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Shipment update(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("shipment can't be null");
        }
        var entity = Shipment.<Shipment>findById(shipment.id);
        if (entity != null) {
            entity.startPort = shipment.startPort;
            entity.endPort = shipment.endPort;
            entity.ship = shipment.ship;
            entity.containers = shipment.containers;
        }
        return entity;
    }

    public static Shipment persistOrUpdate(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("shipment can't be null");
        }
        if (shipment.id == null) {
            persist(shipment);
            return shipment;
        } else {
            return update(shipment);
        }
    }


}
