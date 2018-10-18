package nom.brunokarpo.anotherwebproject.controller

import io.restassured.RestAssured
import io.restassured.http.ContentType
import nom.brunokarpo.anotherwebproject.AnotherWebProjectApplicationTests
import nom.brunokarpo.anotherwebproject.model.Book
import org.hamcrest.Matchers
import org.junit.Test
import org.springframework.http.HttpStatus

class BookControllerTest: AnotherWebProjectApplicationTests() {

    @Test
    fun shouldSaveNewBook() {
        var book: Book = Book.Builder()
                .name("Pai Rico, Pai pobre")
                .author("Robert T. Kiyosaky")
                .build()


        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("user-id", "some-user-id")
                .header("system-id", "system-id")
                .body(book)
                .post("/book")
                .then()
                .log()
                .headers()
                .log()
                .body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("location", Matchers.notNullValue(String::class.java))
                .body("name", Matchers.equalTo("Pai Rico, Pai pobre"),
                        "author", Matchers.equalTo("Robert T. Kiyosaky"),
                        "uuid", Matchers.notNullValue())
    }
}