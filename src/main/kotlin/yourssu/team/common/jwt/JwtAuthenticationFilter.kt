package yourssu.team.common.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import yourssu.team.common.error.ErrorCode
import yourssu.team.common.error.ErrorResponse
import yourssu.team.common.models.MemberRole
import java.security.SignatureException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    private val jwtTokenResolver: JwtTokenResolver,
    private val objectMapper: ObjectMapper
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = jwtTokenResolver.extractTokenFromHttpRequest((request as HttpServletRequest))
        try {
            val authentication = token?.let {
                jwtTokenResolver.validateToken(it)
                jwtTokenResolver.getAuthenticationFromToken(it)
            } ?: getDefaultAuthenticationSession()

            setAuthenticationSession(authentication)
            chain.doFilter(request, response)
        } catch (e: SignatureException) {
            //사인 조작시 발생
            sendErrorMessage(response as HttpServletResponse, ErrorCode.JWT_ERROR)
        } catch (e: ExpiredJwtException) {
            //토큰 만료시 발생
            sendErrorMessage(response as HttpServletResponse, ErrorCode.JWT_EXPIRE)
        } catch (e: MalformedJwtException) {
            //jwt 토큰 형식 안맞을 때 발생
            sendErrorMessage(response as HttpServletResponse, ErrorCode.JWT_ERROR)
        }
    }

    private fun setAuthenticationSession(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getDefaultAuthenticationSession(): Authentication {
        val authorities: List<GrantedAuthority> = Collections
            .singletonList(SimpleGrantedAuthority(MemberRole.ROLE_GUEST.toString()))
        return UsernamePasswordAuthenticationToken(null, null, authorities)
    }

    private fun sendErrorMessage(response: HttpServletResponse, errorCode: ErrorCode) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode)))
    }

}