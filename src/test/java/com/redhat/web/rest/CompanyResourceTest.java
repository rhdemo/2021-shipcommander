package com.redhat.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.redhat.TestUtil;
import com.redhat.service.dto.CompanyDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

    import java.util.List;

@QuarkusTest
public class CompanyResourceTest {

    private static final TypeRef<CompanyDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<CompanyDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";



    String adminToken;

    CompanyDTO companyDTO;

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
    public static CompanyDTO createEntity() {
        var companyDTO = new CompanyDTO();
        companyDTO.name = DEFAULT_NAME;
        companyDTO.addressLine = DEFAULT_ADDRESS_LINE;
        companyDTO.city = DEFAULT_CITY;
        companyDTO.country = DEFAULT_COUNTRY;
        return companyDTO;
    }

    @BeforeEach
    public void initTest() {
        companyDTO = createEntity();
    }

    @Test
    public void createCompany() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Company
        companyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the Company in the database
        var companyDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(companyDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testCompanyDTO = companyDTOList.stream().filter(it -> companyDTO.id.equals(it.id)).findFirst().get();
        assertThat(testCompanyDTO.name).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyDTO.addressLine).isEqualTo(DEFAULT_ADDRESS_LINE);
        assertThat(testCompanyDTO.city).isEqualTo(DEFAULT_CITY);
        assertThat(testCompanyDTO.country).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    public void createCompanyWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Company with an existing ID
        companyDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Company in the database
        var companyDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(companyDTOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void updateCompany() {
        // Initialize the database
        companyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the company
        var updatedCompanyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies/{id}", companyDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the company
        updatedCompanyDTO.name = UPDATED_NAME;
        updatedCompanyDTO.addressLine = UPDATED_ADDRESS_LINE;
        updatedCompanyDTO.city = UPDATED_CITY;
        updatedCompanyDTO.country = UPDATED_COUNTRY;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedCompanyDTO)
            .when()
            .put("/api/companies")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the Company in the database
        var companyDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(companyDTOList).hasSize(databaseSizeBeforeUpdate);
        var testCompanyDTO = companyDTOList.stream().filter(it -> updatedCompanyDTO.id.equals(it.id)).findFirst().get();
        assertThat(testCompanyDTO.name).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyDTO.addressLine).isEqualTo(UPDATED_ADDRESS_LINE);
        assertThat(testCompanyDTO.city).isEqualTo(UPDATED_CITY);
        assertThat(testCompanyDTO.country).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    public void updateNonExistingCompany() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
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
            .body(companyDTO)
            .when()
            .put("/api/companies")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Company in the database
        var companyDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(companyDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCompany() {
        // Initialize the database
        companyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the company
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/companies/{id}", companyDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var companyDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(companyDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllCompanies() {
        // Initialize the database
        companyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the companyList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(companyDTO.id.intValue()))
            .body("name", hasItem(DEFAULT_NAME))            .body("addressLine", hasItem(DEFAULT_ADDRESS_LINE))            .body("city", hasItem(DEFAULT_CITY))            .body("country", hasItem(DEFAULT_COUNTRY));
    }

    @Test
    public void getCompany() {
        // Initialize the database
        companyDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(companyDTO)
            .when()
            .post("/api/companies")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the company
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/companies/{id}", companyDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the company
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies/{id}", companyDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(companyDTO.id.intValue()))
            
                .body("name", is(DEFAULT_NAME))
                .body("addressLine", is(DEFAULT_ADDRESS_LINE))
                .body("city", is(DEFAULT_CITY))
                .body("country", is(DEFAULT_COUNTRY));
    }

    @Test
    public void getNonExistingCompany() {
        // Get the company
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/companies/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
