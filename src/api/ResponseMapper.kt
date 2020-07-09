package com.bath.api

fun <T> toResponse(data: T) = Response(status = Status.SUCCESS, data = data)

fun toResponse(error: ApiError) = Response<Unit>(status = Status.ERROR, error = error)