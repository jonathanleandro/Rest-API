package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ViagensTest {
    @Test
    public void test(){
        //Configurar o caminho comum de acesso e minha API Rest
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        //Login na API Rest com administrador
        String token = given()
                .body("{ \"email\": \"admin@email.com\", \"senha\": \"654321\"}")

                .contentType(ContentType.JSON)
                .when()
                   .post("/v1/auth")
                .then()
                .log().all()
                .extract()
                    .path("data.token");

        System.out.println(token);
        //Cadastrar a viagem
        given()

                .header("Authorization", token)
                .body("{ \"acompanhante\": \"Isabelle\",\"dataPartida\": \"2021-01-23\",\"dataRetorno\": \"2021-02-23\",\"localDeDestino\": \"Manaus\",\"regiao\": \"Norte\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/viagens")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201);
    }
}
