package br.com.challenge.ca;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.service.BankslipsService;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

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
        RestAssured.given().when().get("/rest/bankslips/wert").then().statusCode(400);
    }

    @Test
    public void findByIdErrorBankslipsNotFound() {
        RestAssured.given().when().get("/rest/bankslips/d30cadeb-aa0b-4be5-8f9a-5d49cb128293").then().statusCode(404);
    }

    @Test
    public void findById() {
        BankslipsEntity entity = new BankslipsEntity(new Date(), BigDecimal.valueOf(1000000l), "Fulano de tal");
        BankslipsEntity persisted = bankslipsService.add(entity);

        RestAssured.given().when().get("/rest/bankslips/" + persisted.getCode()).then().statusCode(200);
    }

    @Test
    public void updateErrorBankslipsNotFound() {
        BankslipsUpdateVO updateVO = new BankslipsUpdateVO("PAID");
        RestAssured.given().contentType("application/json").when().body(updateVO).put("/rest/bankslips/d30cadeb-aa0b-4be5-8f9a-5d49cb128293").then().statusCode(404);
    }

    @Test
    public void updateStatus() {
        BankslipsEntity entity = new BankslipsEntity(new Date(), BigDecimal.valueOf(1000000l), "Fulano de tal");
        BankslipsEntity persisted = bankslipsService.add(entity);

        BankslipsUpdateVO updateVO = new BankslipsUpdateVO("PAID");
        RestAssured.given().contentType("application/json").when().body(updateVO).put("/rest/bankslips/" + persisted.getCode()).then().statusCode(200);
    }

    @Test
    public void add() {
        BankslipsEntity entity = new BankslipsEntity(new Date(), BigDecimal.valueOf(1002220000l), "Teste");
        RestAssured.given().contentType("application/json").when().body(entity).post("/rest/bankslips").then().statusCode(200);
    }

    @Test
    public void addErrorBodyNull() {
        BankslipsEntity entity = null;
        RestAssured.given().contentType("application/json").when().body(entity).post("/rest/bankslips").then().statusCode(400);
    }

    @Test
    public void addErrorFieldInvalidValue() {
        BankslipsEntity entity = new BankslipsEntity(new Date(), BigDecimal.valueOf(1002220000l), "");
        RestAssured.given().contentType("application/json").when().body(entity).post("/rest/bankslips").then().statusCode(422);
    }

}
