package yourssu.team.domain.auth.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class MemberJoinRequest(

    @field:NotBlank
    @field:Size(min = 5, max = 20)
    var username: String,

    @field:NotBlank
    @field:Size(min = 5, max = 20)
    var nickname: String,

    @field:NotBlank
    @field:Size(min = 10)
    var password: String

)