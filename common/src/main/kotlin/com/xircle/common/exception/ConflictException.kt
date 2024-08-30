package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

class ConflictException(
    private val baseResponseStatus: BaseResponseStatus
) : BaseException(baseResponseStatus)