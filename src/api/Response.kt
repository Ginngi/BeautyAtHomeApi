package com.bath.api

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val status: Status,
    val data: T? = null,
    val error: ApiError? = null
)