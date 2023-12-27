package com.base.ecomm.data

data class ResourceAPI<out T>(val status: StatusAPI, val data: T?) {
    companion object {

        fun <T> success(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.SUCCESS, data)
        }

        fun <T> tokenExpired(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.TOKEN_EXPIRED, data)
        }

        fun <T> unauthorized(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.UNAUTHORIZED, data)
        }

        fun <T> failed(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.FAILED, data)
        }

        fun <T> error(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.ERROR, data)
        }

        fun <T> maintenance(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.MAINTENANCE, data)
        }

        fun <T> loading(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.LOADING, data)
        }

        fun <T> invalidParameter(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.INVALID_PARAMETER, data)
        }

        fun <T> networkError(data: T?): ResourceAPI<T> {
            return ResourceAPI(StatusAPI.NETWORK_ERROR, data)
        }

    }
}
