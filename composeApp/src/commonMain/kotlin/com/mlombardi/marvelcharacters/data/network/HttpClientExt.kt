package com.mlombardi.marvelcharacters.data.network

import com.mlombardi.marvelcharacters.domain.ResponseWrapper
import com.mlombardi.marvelcharacters.domain.errors.ErrorEntity
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): ResponseWrapper<T> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return ResponseWrapper.Error(ErrorEntity.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return ResponseWrapper.Error(ErrorEntity.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ResponseWrapper.Error(ErrorEntity.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): ResponseWrapper<T> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                ResponseWrapper.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                ResponseWrapper.Error(ErrorEntity.Remote.SERIALIZATION)
            }
        }

        408 -> ResponseWrapper.Error(ErrorEntity.Remote.REQUEST_TIMEOUT)
        429 -> ResponseWrapper.Error(ErrorEntity.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> ResponseWrapper.Error(ErrorEntity.Remote.SERVER)
        else -> ResponseWrapper.Error(ErrorEntity.Remote.UNKNOWN)
    }
}
