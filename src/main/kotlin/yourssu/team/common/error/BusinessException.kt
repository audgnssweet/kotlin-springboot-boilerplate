package yourssu.team.common.error

open class BusinessException(val errorCode: ErrorCode) : RuntimeException()