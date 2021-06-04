package yourssu.team.domain.auth.dto

data class MemberLoginRequest(
    val username: String,
    val password: String
)