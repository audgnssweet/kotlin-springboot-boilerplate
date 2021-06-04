package yourssu.team.common.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import yourssu.team.domain.auth.exception.RefreshTokenNotAcceptableException

class MemberEntityTest {

    @Test
    fun member_makeEntityAndGetPropertiesSucceed() {
        //given
        var member = Member(
            username = "myeonghun",
            password = "12345",
            nickname = "audgnssweet",
            roles = mutableListOf(MemberRole.ROLE_USER)
        )
        //when
        //then
        assertThat(member.username).isEqualTo("myeonghun")
        assertEquals(member.password, "12345")
        assertEquals(member.nickname, "audgnssweet")
        assertThat(member.roles).contains(MemberRole.ROLE_USER)
        assertNull(member.createAt)
        assertNull(member.updateAt)
    }

    @Test
    fun member_checkRefreshTokenInvalidThrow() {
        //given
        var member = Member(
            username = "myeonghun",
            password = "12345",
            nickname = "audgnssweet",
            roles = mutableListOf(MemberRole.ROLE_USER),
            refreshToken = "abc"
        )
        //when
        //then
        assertThrows<RefreshTokenNotAcceptableException> { member.checkRefreshToken("def") }
    }

}