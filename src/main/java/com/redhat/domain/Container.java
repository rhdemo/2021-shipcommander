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
 * A Container.
 */
@Entity
@Table(name = "container")
@Cacheable
@RegisterForReflection
public class Container extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Column(name = "description")
    public String description;

    @OneToOne
    @JoinColumn(unique = true)
    public Company sender;

    @OneToOne
    @JoinColumn(unique = true)
    public Company receiver;

    @OneToMany(mappedBy = "container")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<SensorReadings> readings = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    @JsonbTransient
    public Shipment shipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Container)) {
            return false;
        }
        return id != null && id.equals(((Container) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Container{" +
            "id=" + id +
            ", description='" + description + "'" +
            "}";
    }

    public Container update() {
        return update(this);
    }

    public Container persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Container update(Container container) {
        if (container == null) {
            throw new IllegalArgumentException("container can't be null");
        }
        var entity = Container.<Container>findById(container.id);
        if (entity != null) {
            entity.description = container.description;
            entity.sender = container.sender;
            entity.receiver = container.receiver;
            entity.readings = container.readings;
            entity.shipment = container.shipment;
        }
        return entity;
    }

    public static Container persistOrUpdate(Container container) {
        if (container == null) {
            throw new IllegalArgumentException("container can't be null");
        }
        if (container.id == null) {
            persist(container);
            return container;
        } else {
            return update(container);
        }
    }


}
