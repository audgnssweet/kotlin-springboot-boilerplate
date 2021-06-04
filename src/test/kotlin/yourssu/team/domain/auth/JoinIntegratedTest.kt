package yourssu.team.domain.auth

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import yourssu.team.common.error.ErrorCode
import yourssu.team.domain.auth.dto.MemberJoinRequest

class JoinIntegratedTest : AuthIntegratedTest() {

    @Test
    fun joinMemberSucceed() {
        //given
        val req = MemberJoinRequest(
            username = "succeed",
            nickname = "succeed",
            password = DEFAULT_PASSWORD
        )
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(JOIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(jsonPath("$.username").value("succeed"))
            .andExpect(jsonPath("$.nickname").value("succeed"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun joinMemberUsernameDuplicateFail() {
        //given
        val req = MemberJoinRequest(
            username = DEFAULT_USERNAME,
            nickname = DEFAULT_NICKNAME + 100,
            password = DEFAULT_PASSWORD
        )
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(JOIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(jsonPath("$.error.code").value(ErrorCode.USERNAME_DUPLICATED.code))
            .andExpect(jsonPath("$.error.message").value(Matchers.containsString("duplicated")))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun joinMemberNicknameDuplicateFail() {
        //given
        val req = MemberJoinRequest(
            username = DEFAULT_USERNAME + 100,
            nickname = DEFAULT_NICKNAME,
            password = DEFAULT_PASSWORD
        )
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(JOIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(jsonPath("$.error.code").value(ErrorCode.NICKNAME_DUPLICATED.code))
            .andExpect(jsonPath("$.error.message").value(Matchers.containsString("duplicated")))
            .andDo(MockMvcResultHandlers.print())
    }


}