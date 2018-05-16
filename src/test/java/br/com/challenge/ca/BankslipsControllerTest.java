package br.com.challenge.ca;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.enumeration.BankslipsStatusEnum;
import br.com.challenge.ca.service.BankslipsService;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import br.com.challenge.ca.vo.BankslipsVO;
import io.restassured.RestAssured;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BankslipsControllerTest {

    @Autowired
    private BankslipsService bankslipsService;

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }

    @Test
    public void findByIdErrorInvalidCode() {
        RestAssured.given().when().get("/rest/bankslips/wert").then()
                .statusCode(400)
                .body("message", equalTo("Invalid id provided - it must be a valid UUID"));
    }

    @Test
    public void findByIdErrorBankslipsNotFound() {
        RestAssured.given().when().get("/rest/bankslips/d30cadeb-aa0b-4be5-8f9a-5d49cb128293").then()
                .statusCode(404)
                .body("message", equalTo("Bankslip not found with the specified id"));
    }

    @Test
    public void findByIdNotDue() {
        BankslipsVO entity = new BankslipsVO(DateUtil.now(), BigDecimal.valueOf(1000000L), "Fulano de tal", BankslipsStatusEnum.PENDING);
        BankslipsVO persisted = bankslipsService.add(entity);

        RestAssured.given().when().get("/rest/bankslips/" + persisted.code).then()
                .statusCode(200)
                .body("total_in_cents", equalTo(1000000));
    }

    @Test
    public void findByIdDueBetween5Days() {
        BankslipsVO entity = new BankslipsVO(DateUtil.yesterday(), BigDecimal.valueOf(1000000L), "Fulano de tal", BankslipsStatusEnum.PENDING);
        BankslipsVO persisted = bankslipsService.add(entity);

        RestAssured.given().when().get("/rest/bankslips/" + persisted.code).then()
                .statusCode(200)
                .body("total_in_cents", equalTo(1005000));
    }

    @Test
    public void findByIdDueBetween10Days() {
        BankslipsVO entity = new BankslipsVO(DateUtils.addDays(DateUtil.yesterday(), -10), BigDecimal.valueOf(1000000L), "Fulano de tal", BankslipsStatusEnum.PENDING);
        BankslipsVO persisted = bankslipsService.add(entity);

        RestAssured.given().when().get("/rest/bankslips/" + persisted.code).then()
                .statusCode(200)
                .body("total_in_cents", equalTo(1010000));
    }

    @Test
    public void updateErrorBankslipsNotFound() {
        BankslipsUpdateVO updateVO = new BankslipsUpdateVO(BankslipsStatusEnum.PAID);
        RestAssured.given().contentType("application/json").when().body(updateVO).put("/rest/bankslips/d30cadeb-aa0b-4be5-8f9a-5d49cb128293").then()
                .statusCode(404)
                .body("message", equalTo("Bankslip not found with the specified id"));
    }

    @Test
    public void updateStatus() {
        BankslipsVO entity = new BankslipsVO(new Date(), BigDecimal.valueOf(1000000L), "Fulano de tal", BankslipsStatusEnum.PENDING);
        BankslipsVO persisted = bankslipsService.add(entity);

        BankslipsUpdateVO updateVO = new BankslipsUpdateVO(BankslipsStatusEnum.PAID);
        RestAssured.given().contentType("application/json").when().body(updateVO).put("/rest/bankslips/" + persisted.code).then().statusCode(200);
    }

    @Test
    public void add() {
        BankslipsVO entity = new BankslipsVO(new Date(), BigDecimal.valueOf(1002220000L), "Teste", BankslipsStatusEnum.PENDING);
        RestAssured.given().contentType("application/json").when().body(entity).post("/rest/bankslips").then().statusCode(201);
    }

    @Test
    public void addErrorBodyNull() {
        RestAssured.given().contentType("application/json").when().post("/rest/bankslips").then()
                .statusCode(400)
                .body("message", equalTo("Bankslip not provided in the request body"));
    }

    @Test
    public void addErrorFieldInvalidValue() {
        BankslipsEntity entity = new BankslipsEntity(new Date(), BigDecimal.valueOf(1002220000L), "", BankslipsStatusEnum.PENDING);
        RestAssured.given().contentType("application/json").when().body(entity).post("/rest/bankslips").then()
                .statusCode(422)
                .body("message", equalTo("Invalid bankslip provided.The possible reasons are:" +
                        "A field of the provided bankslip was null or with invalid values"));
    }

}
