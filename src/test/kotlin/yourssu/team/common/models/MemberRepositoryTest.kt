package yourssu.team.common.models

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import yourssu.team.base.BaseJpaTest

class MemberRepositoryTest: BaseJpaTest() {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun memberSaveAndAssertCreatedAndLastModifiedDateAutoFill() {
        //given
        var member = Member(
            username = "myeonghun",
            password = "12345",
            nickname = "audgnssweet",
            roles = mutableListOf(MemberRole.ROLE_USER)
        )
        //when
        val saved = memberRepository.save(member)
        //then
        assertNotNull(saved.id)
        assertNotNull(saved.createAt)
        assertNotNull(saved.updateAt)
    }

}