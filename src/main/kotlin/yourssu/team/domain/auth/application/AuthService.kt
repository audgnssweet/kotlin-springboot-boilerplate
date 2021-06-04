package yourssu.team.domain.auth.application

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yourssu.team.common.jwt.JwtTokenProvider
import yourssu.team.common.jwt.JwtTokenResolver
import yourssu.team.common.models.Member
import yourssu.team.common.models.MemberRepository
import yourssu.team.common.models.MemberRole
import yourssu.team.domain.auth.dto.*
import yourssu.team.domain.auth.exception.MemberPasswordMismatchException
import yourssu.team.domain.auth.exception.NicknameDuplicateException
import yourssu.team.domain.auth.exception.UserNotFoundException
import yourssu.team.domain.auth.exception.UsernameDuplicateException

@Transactional
@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtTokenResolver: JwtTokenResolver
) {

    fun join(dto: MemberJoinRequest): MemberJoinResponse {
        memberRepository.findByUsername(dto.username)?.let { throw UsernameDuplicateException() }
        memberRepository.findByNickname(dto.nickname)?.let { throw NicknameDuplicateException() }

        val saved = memberRepository.save(
            Member(
                username = dto.username,
                password = passwordEncoder.encode(dto.password),
                nickname = dto.nickname,
                roles = mutableListOf(MemberRole.ROLE_USER)
            )
        )

        return MemberJoinResponse.from(saved)
    }

    fun login(dto: MemberLoginRequest): TokenDto {
        val member = memberRepository.findByUsername(dto.username) ?: throw UserNotFoundException()
        if (!member.checkPassword(dto.password)) {
            throw MemberPasswordMismatchException()
        }

        val accessToken = jwtTokenProvider.createAccessToken(member)
        val refreshToken = jwtTokenProvider.createRefreshToken(member)
        member.updateRefreshToken(refreshToken)

        return TokenDto.from(accessToken, refreshToken)
    }

    fun changePassword(loginUsername: String, dto: MemberPasswordChangeRequest) {
        val foundMember =
            memberRepository.findByUsername(loginUsername) ?: throw UserNotFoundException()

        if (!foundMember.checkPassword(dto.existingPassword)) {
            throw MemberPasswordMismatchException()
        } else foundMember.updatePassword(dto.newPassword)
    }

    fun refreshToken(dto: TokenRefreshRequestDto): TokenDto {
        val username = jwtTokenResolver.getAuthenticationFromToken(dto.token).principal as String
        val member = memberRepository.findByUsername(username) ?: throw UserNotFoundException()
        member.checkRefreshToken(dto.token)

        val accessToken = jwtTokenProvider.createAccessToken(member)
        val refreshToken = jwtTokenProvider.createRefreshToken(member)
        member.updateRefreshToken(dto.token)

        return TokenDto.from(accessToken, refreshToken)
    }

}