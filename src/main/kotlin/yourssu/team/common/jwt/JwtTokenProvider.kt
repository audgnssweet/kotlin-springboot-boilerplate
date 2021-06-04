package yourssu.team.common.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import yourssu.team.common.models.Member
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider : InitializingBean {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    private lateinit var key: Key

    override fun afterPropertiesSet() {
        key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    fun createAccessToken(login: Member): String {
        return createToken(login, JwtProperties.ACCESS_EXPIRATION_TIME)
    }

    fun createRefreshToken(login: Member): String {
        return createToken(login, JwtProperties.REFRESH_EXPIRATION_TIME)
    }

    fun createToken(login: Member, expirationTime: Long): String {
        //Header
        val headers: MutableMap<String, Any> = HashMap()
        headers["typ"] = JwtProperties.TYP
        //Payload
        val payload: MutableMap<String, Any?> = HashMap()
        payload["username"] = login.username
        payload["roles"] = login.roles
        //Expiration Time
        var exp = Date()
        exp.time = exp.time + expirationTime

        return Jwts.builder()
            .setHeader(headers)
            .setClaims(payload)
            .setIssuedAt(Date())
            .setExpiration(exp)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

}