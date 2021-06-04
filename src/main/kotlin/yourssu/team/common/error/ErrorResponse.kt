package yourssu.team.common.error

data class ErrorResponse(
    val error : Error
) {

    companion object {
        fun of(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(Error(errorCode.status, errorCode.code, errorCode.message))
        }
    }

}