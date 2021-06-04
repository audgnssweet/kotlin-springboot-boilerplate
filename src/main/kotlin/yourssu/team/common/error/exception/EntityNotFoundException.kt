package yourssu.team.common.error.exception

import yourssu.team.common.error.BusinessException
import yourssu.team.common.error.ErrorCode

open class EntityNotFoundException(errorCode: ErrorCode) : BusinessException(errorCode)