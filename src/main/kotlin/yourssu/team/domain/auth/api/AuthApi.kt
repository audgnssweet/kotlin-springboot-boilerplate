package yourssu.team.domain.auth.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import yourssu.team.common.jwt.LoginMember
import yourssu.team.domain.auth.application.AuthService
import yourssu.team.domain.auth.dto.*
import javax.validation.Valid

@RestController
class AuthApi(
    private val authService: AuthService
) {

    @PostMapping("/auth/join")
    @ResponseStatus(HttpStatus.CREATED)
    fun join(@Valid @RequestBody dto: MemberJoinRequest) : MemberJoinResponse {
        return authService.join(dto)
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@Valid @RequestBody dto: MemberLoginRequest) : TokenDto {
        return authService.login(dto)
    }

    @PostMapping("/auth/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    fun refreshToken(@Valid @RequestBody dto: TokenRefreshRequestDto) : TokenDto {
        return authService.refreshToken(dto)
    }

    @PatchMapping("/members/password")
    @ResponseStatus(HttpStatus.CREATED)
    fun changePassword(
        @LoginMember loginUsername: String,
        @Valid @RequestBody dto: MemberPasswordChangeRequest
    ) {
        authService.changePassword(loginUsername, dto)
    }

}