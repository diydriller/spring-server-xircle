package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

class ServerErrorException(
    private val baseResponseStatus: BaseResponseStatus
) : BaseException(baseResponseStatus)