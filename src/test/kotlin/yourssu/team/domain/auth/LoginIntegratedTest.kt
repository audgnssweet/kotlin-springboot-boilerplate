package yourssu.team.domain.auth

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import yourssu.team.common.error.ErrorCode
import yourssu.team.domain.auth.dto.MemberLoginRequest

class LoginIntegratedTest : AuthIntegratedTest() {

    @Test
    fun loginSucceed() {
        //given
        val req = MemberLoginRequest(DEFAULT_USERNAME, DEFAULT_PASSWORD)
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.accessToken").value(Matchers.notNullValue()))
            .andExpect(jsonPath("$.refreshToken").value(Matchers.notNullValue()))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun loginMemberNotFoundFail() {
        //given
        val req = MemberLoginRequest("notUserButOnlyTest", DEFAULT_PASSWORD)
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(jsonPath("$.error.code").value(ErrorCode.MEMBER_NOT_FOUND.code))
            .andExpect(jsonPath("$.error.message").value(Matchers.containsString("not found")))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun loginMemberPasswordMismatchFail() {
        //given
        val req = MemberLoginRequest(DEFAULT_USERNAME, "incorrectPassword")
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(jsonPath("$.error.code").value(ErrorCode.MEMBER_PASSWD_MISMATCH.code))
            .andExpect(jsonPath("$.error.message").value(Matchers.containsString("not match")))
            .andDo(MockMvcResultHandlers.print())
    }

}