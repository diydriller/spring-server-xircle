package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

class ConflictException(
    val baseResponseStatus: BaseResponseStatus
) : BaseException(baseResponseStatus)