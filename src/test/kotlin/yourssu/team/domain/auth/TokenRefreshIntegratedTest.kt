package yourssu.team.domain.auth

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import yourssu.team.common.error.ErrorCode
import yourssu.team.domain.auth.dto.TokenRefreshRequestDto

class TokenRefreshIntegratedTest : AuthIntegratedTest() {

    @Test
    fun refreshTokenSucceed() {
        //given
        val token = makeMemberLoginAndGetToken(
            DEFAULT_USERNAME,
            DEFAULT_PASSWORD,
            refresh = true
        )
        val req = this.objectMapper.writeValueAsString(TokenRefreshRequestDto(token))
        //when
        val resultActions = this.mockMvc.perform(
            post(REFRESH_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(req)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated)
            .andExpect(jsonPath("$.accessToken").value(Matchers.notNullValue()))
            .andExpect(jsonPath("$.refreshToken").value(Matchers.notNullValue()))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun refreshTokenInvalidTokenFormFail() {
        val token = makeMemberLoginAndGetToken(
            DEFAULT_USERNAME,
            DEFAULT_PASSWORD,
            refresh = true
        )
        val req = this.objectMapper.writeValueAsString(TokenRefreshRequestDto(token.toUpperCase()))
        //when
        val resultActions = this.mockMvc.perform(
            post(REFRESH_ADDR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(req)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error.status").value(ErrorCode.JWT_ERROR.status))
            .andExpect(jsonPath("$.error.code").value(ErrorCode.JWT_ERROR.code))
            .andExpect(jsonPath("$.error.message").value(ErrorCode.JWT_ERROR.message))
            .andDo(MockMvcResultHandlers.print())
    }

}