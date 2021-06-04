package yourssu.team.common.models

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>{
    fun findByUsername(username: String) : Member?
    fun findByNickname(nickname: String) : Member?
}