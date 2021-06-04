package yourssu.team.common.models

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import yourssu.team.domain.auth.exception.RefreshTokenNotAcceptableException
import javax.persistence.*

@Table(name = "member")
@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false, updatable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false, updatable = false, unique = true)
    val nickname: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    var roles: MutableList<MemberRole> = mutableListOf(),

    @Column(name = "refresh_token")
    var refreshToken: String? = null

) : BaseTimeEntity(){

    fun checkPassword(password: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.matches(password, this.password)
    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun checkRefreshToken(refreshToken: String) {
        if (!this.refreshToken.equals(refreshToken)) {
            throw RefreshTokenNotAcceptableException()
        }
    }

    fun updatePassword(newPassword: String) {
        val passwordEncoder = BCryptPasswordEncoder()
        this.password = passwordEncoder.encode(newPassword)
    }

}