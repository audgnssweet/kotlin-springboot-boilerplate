package yourssu.team.domain.auth.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class MemberPasswordChangeRequest(

    @field:NotBlank
    @field:Size(min = 10)
    val existingPassword: String,

    @field:NotBlank
    @field:Size(min = 10)
    val newPassword: String
)