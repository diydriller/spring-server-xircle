package com.xircle.common.exception

import com.xircle.common.response.BaseResponseStatus

class AuthenticationException(
    private val baseResponseStatus: BaseResponseStatus
) : BaseException(baseResponseStatus)