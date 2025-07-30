package com.mlombardi.marvelcharacters.domain.errors

/**
 * Types of errors (Internal, or Network)
 */
sealed interface ErrorEntity {

    enum class Remote : ErrorEntity {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : ErrorEntity {
        DISK_FULL,
        UNKNOWN
    }
}
