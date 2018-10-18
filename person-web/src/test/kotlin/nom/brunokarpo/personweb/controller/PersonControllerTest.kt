package nom.brunokarpo.personweb.controller

import io.restassured.RestAssured
import io.restassured.http.ContentType
import nom.brunokarpo.personweb.PersonWebApplicationTests
import nom.brunokarpo.personweb.model.Person
import org.hamcrest.Matchers
import org.junit.Test
import org.springframework.http.HttpStatus

class PersonControllerTest: PersonWebApplicationTests() {

    @Test
    fun shouldSaveNewPerson() {
        var person = Person.Builder()
                .name("Bruno Nogueira")
                .age(28)
                .build()

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("user-id", "some-user-id")
                .header("system-id", "system-id")
                .body(person)
                .post("/person")
                .then()
                .log()
                .headers()
                .log()
                .body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("location", Matchers.notNullValue(String::class.java))
                .body("name", Matchers.equalTo("Bruno Nogueira"),
                        "age", Matchers.equalTo(28),
                        "uuid", Matchers.notNullValue())
    }

    @Test
    fun shouldUpdatePerson() {
        var person = Person.Builder().name("Eliana").age(51).build()

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("user-id", "some-user-id")
                .header("system-id", "some-system-id")
                .body(person)
                .put("/person/8e432579-e507-4cf6-a923-c35550a3ef04")
                .then()
                .log()
                .headers()
                .log()
                .body()
                .and()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("Eliana"),
                        "age", Matchers.equalTo(51),
                        "uuid", Matchers.equalTo("8e432579-e507-4cf6-a923-c35550a3ef04"))

    }

    @Test
    fun shouldReturnNotFoundForInexistentUUID() {
        var person = Person.Builder().name("Eliana").age(51).build()

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("user-id", "some-user-id")
                .header("system-id", "some-system-id")
                .body(person)
                .put("/person/8ded8b20-2ffd-4e27-9c6a-5e5b3445c817")
                .then()
                .log()
                .headers()
                .and()
                .statusCode(HttpStatus.NOT_FOUND.value())
    }
}