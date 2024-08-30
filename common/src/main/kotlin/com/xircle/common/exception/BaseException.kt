package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

open class BaseException(private val status: BaseResponseStatus) : RuntimeException(status.message)
