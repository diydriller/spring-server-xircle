package com.xircle.common.util

class StringUtil {
    companion object {
        fun getPostKey(followerId: Long): String {
            return "post:$followerId"
        }

        const val SAVE_POST_TO_FOLLOWER_TOPIC = "save-post-to-follower"
        const val CREATE_USER_TOPIC = "user-created"
    }
}