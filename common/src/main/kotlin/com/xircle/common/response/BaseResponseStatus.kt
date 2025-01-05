package com.xircle.common.response

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
) {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    FOLLOW_SUCCESS(true, 1001, "팔로우가 되었습니다."),
    UNFOLLOW_SUCCESS(true, 1002, "팔로우 취소가 되었습니다."),

    ALREADY_EXIST_EMAIL(false, 2000, "이미 존재하는 이메일입니다."),
    ALREADY_EXIST_NAME(false, 2001, "이미 존재하는 별명입니다."),
    NOT_EXIST_EMAIL(false, 2002, "존재하지 않는 이메일입니다."),
    NOT_EQUAL_PASSWORD(false, 2003, "일치하지 않는 비밀번호입니다."),
    NOT_EXIST_MEMBER(false, 2004, "존재하지 않는 멤버입니다."),
    NOT_AVAILABLE_COUPON(false, 2005, "이용가능한 쿠폰이 없습니다."),

    NOT_EXIST_POST(false, 3000, "존재하지 않는 게시물입니다."),
    NOT_EXIST_COMMENT(false, 3001, "존재하지 않는 댓글입니다."),

    NOT_AUTHENTICATION_ERROR(false, 4001, "인증에 실패했습니다."),
    NOT_AUTHORIZATION_ERROR(false, 4003, "권한이 없습니다."),

    ILLEGAL_ARGUMENT_ERROR(false, 5001, "서버 - 잘못된 인자 오류.")
}