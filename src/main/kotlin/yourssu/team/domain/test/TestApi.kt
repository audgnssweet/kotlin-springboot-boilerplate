package yourssu.team.domain.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestApi {

    @GetMapping("/test")
    fun test(): String {
        return "<h1>test</h1>"
    }

}