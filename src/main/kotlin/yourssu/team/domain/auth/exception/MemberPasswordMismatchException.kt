package yourssu.team.domain.auth.exception

import yourssu.team.common.error.ErrorCode
import yourssu.team.common.error.exception.InputValueNotAcceptableException

class MemberPasswordMismatchException() : InputValueNotAcceptableException(ErrorCode.MEMBER_PASSWD_MISMATCH)