package com.xircle.core.domain.post.cache

import com.xircle.common.util.StringUtil.Companion.getFolloweeKey
import com.xircle.common.util.StringUtil.Companion.getPostKey
import com.xircle.core.domain.post.dto.GetPostInfo
import com.xircle.core.domain.post.model.Post
import com.xircle.core.repository.post.PostJpaRepository
import com.xircle.core.repository.post.PostRedisRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class PostCacheAspect(
    private val postRedisRepository: PostRedisRepository,
    private val postJpaRepository: PostJpaRepository
) {
    @Around("@annotation(UpdatePostCache) && execution(* *(..))")
    fun updatePostCache(joinPoint: ProceedingJoinPoint): Any? {
        val result = joinPoint.proceed()
        val post = result as Post

        val followeeId = post.memberId
        val followeeKey = getFolloweeKey(followeeId)
        postRedisRepository.updateFollowerPost(followeeKey, post.id as Long)

        return result
    }

    @Around("@annotation(GetPostCache) && args(page, size, memberId,..)")
    fun getPostCache(joinPoint: ProceedingJoinPoint, page: Int, size: Int, memberId: Long): Any? {
        val postKey = getPostKey(memberId)
        val start = page * size
        val end = start + size - 1
        val postIdList = postRedisRepository.getPagedPostIdList(postKey, start.toLong(), end.toLong())
        if (postIdList.isEmpty()) {
            return joinPoint.proceed();
        }

        return postJpaRepository.findAllByIsDeletedAndIdIn(false, postIdList)
            .map {
                GetPostInfo(
                    it.id as Long,
                    it.title,
                    it.content,
                    it.postImgSrc,
                    it.createdAt as LocalDateTime,
                    it.hashtagList.map { hashtag -> hashtag.name }
                )
            }
    }
}