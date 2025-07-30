package com.mlombardi.marvelcharacters.core.domain.errors

/**
 * Interface for the error handler
 */
interface IErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}
