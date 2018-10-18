package nom.brunokarpo.personweb

import io.restassured.RestAssured
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class PersonWebApplicationTests {

    @LocalServerPort
    private var port: Int? = null

    @Before
    fun setUp() {
        RestAssured.port = port!!.toInt()
    }
}
