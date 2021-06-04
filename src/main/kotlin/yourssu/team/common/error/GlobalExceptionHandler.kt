package yourssu.team.common.error

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleUsernameDuplicateException(e: BusinessException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse.of(e.errorCode), HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(JwtException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJwtException(e: JwtException): ErrorResponse {
        return ErrorResponse.of(ErrorCode.JWT_ERROR)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleExpiredJwtException(e: ExpiredJwtException): ErrorResponse {
        return ErrorResponse.of(ErrorCode.JWT_EXPIRE)
    }

}