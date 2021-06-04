package yourssu.team.domain.auth.dto

import javax.validation.constraints.NotBlank

data class TokenRefreshRequestDto(
    @field:NotBlank
    val token: String
)