package yourssu.team.domain.auth.exception

import yourssu.team.common.error.ErrorCode
import yourssu.team.common.error.exception.EntityNotFoundException

class UserNotFoundException() : EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND)