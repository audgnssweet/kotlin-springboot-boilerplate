package yourssu.team.domain.auth

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import yourssu.team.base.BaseApiIntegratedTest
import yourssu.team.common.models.Member
import yourssu.team.common.models.MemberRepository
import yourssu.team.common.models.MemberRole
import yourssu.team.domain.auth.application.AuthService
import yourssu.team.domain.auth.dto.MemberLoginRequest

class AuthIntegratedTest : BaseApiIntegratedTest() {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    companion object {
        const val JOIN_ADDR = "/auth/join"
        const val LOGIN_ADDR = "/auth/login"
        const val PASSWD_CHANGE_ADDR = "/members/password"
        const val REFRESH_ADDR = "/auth/refresh"

        const val DEFAULT_USERNAME = "audgnssweet"
        const val DEFAULT_PASSWORD = "123123123123"
        const val DEFAULT_NICKNAME = "audgnssweet"
    }

    @BeforeEach
    fun init() {
        val member = Member(
            username = DEFAULT_USERNAME,
            password = bCryptPasswordEncoder.encode(DEFAULT_PASSWORD),
            nickname = DEFAULT_NICKNAME,
            roles = mutableListOf(MemberRole.ROLE_USER),
        )
        memberRepository.save(member)
    }

    fun makeMemberLoginAndGetToken(username: String, password: String, refresh: Boolean): String {
        val dto = MemberLoginRequest(username, password)
        val resp = authService.login(dto)
        return if(refresh) resp.refreshToken else resp.accessToken
    }

}