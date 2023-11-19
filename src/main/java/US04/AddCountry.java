package US04;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class AddCountry {
    RequestSpecification reqSpec;
    Faker random = new Faker();
    String rnName = "";
    String rnCode = "";
    String countryID = "";

    @BeforeClass
    public void setup() {
        baseURI = "https://test.mersys.io/";
        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", "turkeyts");
        loginData.put("password", "TechnoStudy123");
        loginData.put("rememberMe", "ture");
        Cookies cookies =
                given()
                        .body(loginData)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response().detailedCookies();
        reqSpec = new RequestSpecBuilder()
                .addCookies(cookies)
                .setContentType(ContentType.JSON)
                .build();
    }
    @Test
    public void createCountry(){
        //{
        //  "id": null,
        //  "name": "karahan",
        //  "code": "karahan123",
        //  "translateName": [],
        //  "hasState": false
        //}
        rnName = random.name().firstName();
        rnCode = random.code().asin();
        List<Object> translateNameList = new ArrayList<>();
        Map<String,Object> newCountry = new HashMap<>();
        newCountry.put("id",null);
        newCountry.put("name",rnName);
        newCountry.put("code",rnCode);
        newCountry.put("translateName",translateNameList);
        newCountry.put("hasState",false);
        countryID =
                given()
                        .spec(reqSpec)
                        .body(newCountry)
                        .when()
                        .post("school-service/api/countries")
                        .then()
                        .statusCode(201)
                        .extract().path("id");

    }
}
