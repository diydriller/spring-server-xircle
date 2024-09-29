package com.xircle.common.util

class StringUtil {
    companion object{
        fun getFollowerKey(followerId: Long): String {
            return "member:$followerId:following"
        }

        fun getFolloweeKey(followeeId: Long): String {
            return "member:$followeeId:followers"
        }

        fun getPostKey(memberId: Long): String {
            return "member:\"$memberId\":posts"
        }
    }
}