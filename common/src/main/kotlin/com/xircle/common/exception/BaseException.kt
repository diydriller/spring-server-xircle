package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

open class BaseException(val status: BaseResponseStatus) : RuntimeException(status.message)
