package yourssu.team.domain.auth.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(accessToken: String, refreshToken: String): TokenDto {
            return TokenDto(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}