package com.mlombardi.marvelcharacters.core.data.errors

import com.mlombardi.marvelcharacters.core.domain.errors.ErrorEntity
import com.mlombardi.marvelcharacters.core.domain.errors.IErrorHandler
import kotlinx.io.IOException

class ErrorHandler : IErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Remote.SERVER
            else -> {
                ErrorEntity.Local.UNKNOWN
            }
        }
    }
}
