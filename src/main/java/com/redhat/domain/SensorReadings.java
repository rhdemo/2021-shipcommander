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
 * A SensorReadings.
 */
@Entity
@Table(name = "sensor_readings")
@Cacheable
@RegisterForReflection
public class SensorReadings extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Column(name = "temperature")
    public Double temperature;

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longtitude")
    public Double longtitude;

    @Column(name = "humidity_rate")
    public Double humidityRate;

    @ManyToOne
    @JoinColumn(name = "container_id")
    @JsonbTransient
    public Container container;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorReadings)) {
            return false;
        }
        return id != null && id.equals(((SensorReadings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SensorReadings{" +
            "id=" + id +
            ", temperature=" + temperature +
            ", latitude=" + latitude +
            ", longtitude=" + longtitude +
            ", humidityRate=" + humidityRate +
            "}";
    }

    public SensorReadings update() {
        return update(this);
    }

    public SensorReadings persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static SensorReadings update(SensorReadings sensorReadings) {
        if (sensorReadings == null) {
            throw new IllegalArgumentException("sensorReadings can't be null");
        }
        var entity = SensorReadings.<SensorReadings>findById(sensorReadings.id);
        if (entity != null) {
            entity.temperature = sensorReadings.temperature;
            entity.latitude = sensorReadings.latitude;
            entity.longtitude = sensorReadings.longtitude;
            entity.humidityRate = sensorReadings.humidityRate;
            entity.container = sensorReadings.container;
        }
        return entity;
    }

    public static SensorReadings persistOrUpdate(SensorReadings sensorReadings) {
        if (sensorReadings == null) {
            throw new IllegalArgumentException("sensorReadings can't be null");
        }
        if (sensorReadings.id == null) {
            persist(sensorReadings);
            return sensorReadings;
        } else {
            return update(sensorReadings);
        }
    }


}
