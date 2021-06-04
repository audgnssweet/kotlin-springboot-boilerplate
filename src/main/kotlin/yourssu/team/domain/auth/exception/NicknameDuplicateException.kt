package yourssu.team.domain.auth.exception

import yourssu.team.common.error.ErrorCode
import yourssu.team.common.error.exception.InputValueNotAcceptableException

class NicknameDuplicateException() : InputValueNotAcceptableException(ErrorCode.NICKNAME_DUPLICATED)