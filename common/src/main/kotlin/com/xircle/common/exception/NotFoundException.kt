package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

class NotFoundException(
    val baseResponseStatus: BaseResponseStatus
) : BaseException(baseResponseStatus)