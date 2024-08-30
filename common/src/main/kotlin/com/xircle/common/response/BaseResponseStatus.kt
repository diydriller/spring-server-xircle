package com.xircle.common.response

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
){
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    ALREADY_EXIST_EMAIL(false,2000,"이미 존재하는 이메일입니다."),
    ALREADY_EXIST_NAME(false,2001,"이미 존재하는 별명입니다."),
    NOT_EXIST_EMAIL(false,2002,"존재하지 않는 이메일입니다."),
    NOT_EQUAL_PASSWORD(false,2003,"일치하지 않는 비밀번호입니다."),
    NOT_EXIST_USER(false,2004,"존재하지 않는 유저입니다."),

    AUTHENTICATION_ERROR(false,2101,"인증되지 않았습니다."),

    ILLEGAL_ARGUMENT_ERROR(false, 5001, "서버 - 잘못된 인자 오류.")
}