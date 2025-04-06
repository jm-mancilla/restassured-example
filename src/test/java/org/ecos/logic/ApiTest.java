package org.ecos.logic;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ApiTest extends Book {
    private static final AllureRestAssured allureFilter = new AllureRestAssured();
    private RequestSpecification httpRequest;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/";
        // -- --------------------------------------------------------- --
        RestAssured.useRelaxedHTTPSValidation();
        // -- --------------------------------------------------------- --

        httpRequest = given().
                filter(allureFilter);
        Response response = httpRequest.when().
                get("/books").
                //Assertion
                        then().
                statusCode(200).
                extract().response();

        //Assertions
        List<Book> allBooks = response.jsonPath().getList("books", Book.class);

    }

    @Test
    void getBooksDetails() {
        //Arrange

        //Act
        Response response = httpRequest.when().
            get("/books").
        //Assertion
            then().
                statusCode(200).
                extract().response();

        //Assertions
       List<Book> allBooks = response.jsonPath().getList("books", Book.class);


        assertThat(allBooks).hasSize(8);
    }

    @Test
    void searchBook() {
        // Arrange

        // Act
    Response response2 = httpRequest.when()
            .get("/Book?ISBN=9781449325862")
            .then()
            .statusCode(200)
            .extract().response();

    String jsonTitle = response2.jsonPath().getString("title");

    assertThat(jsonTitle).isEqualToIgnoringCase("Git Pocket Guide");

    }

    @Test
    void addBookNonAuthorizedUser(){

       // List<String> isbnCollection = Arrays.asList("2222222222222","3333333333333","4444444444444");
       // User user = new User("89785",isbnCollection.get(0));
        String requestBody = """
                {
                   "userId": "89785",
                   "collectionOfIsbns": [
                     {
                       "isbn": "2222222222222"
                     }
                   ]
                 }
                """;

        Response response = httpRequest
                .contentType("application/json")
                .body(requestBody) //Seguro que se puede hacer sin hardcodear valores
                .when()
                .post("/Books")
                .then()
                .statusCode(401)
                .extract()
                .response();

        String errorMessage = response.jsonPath().getString("message");

        assertThat(errorMessage).isEqualToIgnoringCase("User not authorized!");
        System.out.println(errorMessage);
    }
}


// dudas: se puede hacer más de una llamada (Response) por test? De ese modo puedo obtener la lista de libros (sigue abajo)
// y se podría recuperar el isbn sin necesidad de hardcodearlo (lo intente pero solo hace la primera petición de las 2)
// ¿por que se necesita como parámetro la clase libro para almacenar el listado de libros?
// ¿por qué el reporte del tercer test me sigue poniendo Curl GET cuando estoy haciendo un POST?