package yourssu.team.domain.test

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import yourssu.team.base.BaseApiIntegratedTest

class TestApiIntegratedTest: BaseApiIntegratedTest() {

    @Test
    fun test() {
        //given
        //when
        val resultActions = this.mockMvc.perform(
            get("/test")
        )
        //then
        resultActions
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

}