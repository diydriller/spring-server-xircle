package com.xircle.common.response

data class BaseResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val code: Int,
    val data: T? = null
) {
    constructor(data: T) : this(
        isSuccess = BaseResponseStatus.SUCCESS.isSuccess,
        message = BaseResponseStatus.SUCCESS.message,
        code = BaseResponseStatus.SUCCESS.code,
        data = data
    )

    constructor(status: BaseResponseStatus) : this(
        isSuccess = status.isSuccess,
        message = status.message,
        code = status.code
    )
}