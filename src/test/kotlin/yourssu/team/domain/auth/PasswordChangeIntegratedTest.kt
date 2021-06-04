package yourssu.team.domain.auth

import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import yourssu.team.common.error.ErrorCode
import yourssu.team.common.jwt.JwtProperties
import yourssu.team.domain.auth.dto.MemberPasswordChangeRequest

class PasswordChangeIntegratedTest : AuthIntegratedTest() {

    @Test
    fun passwordChangeSucceed() {
        //given
        val token = makeMemberLoginAndGetToken(
            DEFAULT_USERNAME,
            DEFAULT_PASSWORD,
            refresh = false
        )
        val req = MemberPasswordChangeRequest(DEFAULT_PASSWORD, "1234512345")
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.patch(PASSWD_CHANGE_ADDR)
                .header(JwtProperties.HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun passwordChangeExistingPasswordNotMatchFail() {
        //given
        val token = makeMemberLoginAndGetToken(
            DEFAULT_USERNAME,
            DEFAULT_PASSWORD,
            refresh = false
        )
        val req = MemberPasswordChangeRequest("1234512345", "1234512345")
        val request = this.objectMapper.writeValueAsString(req)
        //when
        val resultActions = this.mockMvc.perform(
            MockMvcRequestBuilders.patch(PASSWD_CHANGE_ADDR)
                .header(JwtProperties.HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(jsonPath("$.error.code").value(ErrorCode.MEMBER_PASSWD_MISMATCH.code))
            .andExpect(jsonPath("$.error.message").value("password not match"))
            .andDo(MockMvcResultHandlers.print())
    }


}