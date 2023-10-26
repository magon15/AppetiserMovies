package com.appetiser.appetisermovies.general

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultOf<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultOf.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultOf.Failure("Network error.")
                else -> {
                    ResultOf.Failure(throwable.localizedMessage ?: "Unknown error.")
                }
            }
        }
    }
}