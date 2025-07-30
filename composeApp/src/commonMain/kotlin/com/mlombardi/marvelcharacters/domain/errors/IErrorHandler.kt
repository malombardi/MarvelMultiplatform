package com.mlombardi.marvelcharacters.domain.errors

/**
 * Interface for the error handler
 */
interface IErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}
