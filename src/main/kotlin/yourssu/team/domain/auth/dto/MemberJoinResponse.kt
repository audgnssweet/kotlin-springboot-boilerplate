package yourssu.team.domain.auth.dto

import yourssu.team.common.models.Member

data class MemberJoinResponse (
    val username: String,
    val nickname: String
) {
    companion object {
        fun from(member: Member) : MemberJoinResponse {
            return MemberJoinResponse(
                username = member.username,
                nickname = member.nickname
            )
        }
    }
}