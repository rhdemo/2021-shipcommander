package com.redhat.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.redhat.TestUtil;
import com.redhat.service.dto.SensorReadingsDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

    import java.util.List;

@QuarkusTest
public class SensorReadingsResourceTest {

    private static final TypeRef<SensorReadingsDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<SensorReadingsDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGTITUDE = 1D;
    private static final Double UPDATED_LONGTITUDE = 2D;

    private static final Double DEFAULT_HUMIDITY_RATE = 1D;
    private static final Double UPDATED_HUMIDITY_RATE = 2D;



    String adminToken;

    SensorReadingsDTO sensorReadingsDTO;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config =
            RestAssured.config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorReadingsDTO createEntity() {
        var sensorReadingsDTO = new SensorReadingsDTO();
        sensorReadingsDTO.temperature = DEFAULT_TEMPERATURE;
        sensorReadingsDTO.latitude = DEFAULT_LATITUDE;
        sensorReadingsDTO.longtitude = DEFAULT_LONGTITUDE;
        sensorReadingsDTO.humidityRate = DEFAULT_HUMIDITY_RATE;
        return sensorReadingsDTO;
    }

    @BeforeEach
    public void initTest() {
        sensorReadingsDTO = createEntity();
    }

    @Test
    public void createSensorReadings() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the SensorReadings
        sensorReadingsDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(sensorReadingsDTO)
            .when()
            .post("/api/sensor-readings")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the SensorReadings in the database
        var sensorReadingsDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(sensorReadingsDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testSensorReadingsDTO = sensorReadingsDTOList.stream().filter(it -> sensorReadingsDTO.id.equals(it.id)).findFirst().get();
        assertThat(testSensorReadingsDTO.temperature).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testSensorReadingsDTO.latitude).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSensorReadingsDTO.longtitude).isEqualTo(DEFAULT_LONGTITUDE);
        assertThat(testSensorReadingsDTO.humidityRate).isEqualTo(DEFAULT_HUMIDITY_RATE);
    }

    @Test
    public void createSensorReadingsWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the SensorReadings with an existing ID
        sensorReadingsDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(sensorReadingsDTO)
            .when()
            .post("/api/sensor-readings")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SensorReadings in the database
        var sensorReadingsDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(sensorReadingsDTOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void updateSensorReadings() {
        // Initialize the database
        sensorReadingsDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(sensorReadingsDTO)
            .when()
            .post("/api/sensor-readings")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the sensorReadings
        var updatedSensorReadingsDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings/{id}", sensorReadingsDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the sensorReadings
        updatedSensorReadingsDTO.temperature = UPDATED_TEMPERATURE;
        updatedSensorReadingsDTO.latitude = UPDATED_LATITUDE;
        updatedSensorReadingsDTO.longtitude = UPDATED_LONGTITUDE;
        updatedSensorReadingsDTO.humidityRate = UPDATED_HUMIDITY_RATE;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedSensorReadingsDTO)
            .when()
            .put("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the SensorReadings in the database
        var sensorReadingsDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(sensorReadingsDTOList).hasSize(databaseSizeBeforeUpdate);
        var testSensorReadingsDTO = sensorReadingsDTOList.stream().filter(it -> updatedSensorReadingsDTO.id.equals(it.id)).findFirst().get();
        assertThat(testSensorReadingsDTO.temperature).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testSensorReadingsDTO.latitude).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSensorReadingsDTO.longtitude).isEqualTo(UPDATED_LONGTITUDE);
        assertThat(testSensorReadingsDTO.humidityRate).isEqualTo(UPDATED_HUMIDITY_RATE);
    }

    @Test
    public void updateNonExistingSensorReadings() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(sensorReadingsDTO)
            .when()
            .put("/api/sensor-readings")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the SensorReadings in the database
        var sensorReadingsDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(sensorReadingsDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSensorReadings() {
        // Initialize the database
        sensorReadingsDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(sensorReadingsDTO)
            .when()
            .post("/api/sensor-readings")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the sensorReadings
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/sensor-readings/{id}", sensorReadingsDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var sensorReadingsDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(sensorReadingsDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

   
    @Test
    public void getNonExistingSensorReadings() {
        // Get the sensorReadings
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/sensor-readings/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
