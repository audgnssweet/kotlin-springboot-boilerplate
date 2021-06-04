package yourssu.team.common.jwt

class JwtProperties {

    private constructor()

    companion object {
        const val TYP = "JWT"
        const val HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val ACCESS_EXPIRATION_TIME = 1000L * 60 * 30
        const val REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7
    }

}