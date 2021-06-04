package yourssu.team.common.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenResolver : InitializingBean{

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    private lateinit var key: Key

    override fun afterPropertiesSet() {
        key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    fun extractTokenFromHttpRequest(request: HttpServletRequest): String? {
        val token = request.getHeader(JwtProperties.HEADER)
        return token?.replace(JwtProperties.TOKEN_PREFIX, "")
    }

    fun validateToken(token: String?) {
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
    }

    fun getAuthenticationFromToken(token: String?): Authentication {
        val payload = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val id = payload["username"] as String
        val roles = payload["roles"] as MutableList<String>
        val authorities = convertStringToAuthority(roles)
        return UsernamePasswordAuthenticationToken(id, "", authorities)
    }

    private fun convertStringToAuthority(roles: List<String>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }.toCollection(mutableListOf())
    }

}