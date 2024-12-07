package com.xircle.eventservice.infrastructure.store

import com.xircle.eventservice.domain.integration.store.CouponStore
import com.xircle.eventservice.domain.model.Coupon
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class CouponStoreImpl(
    private val jdbcTemplate: JdbcTemplate
) : CouponStore {
    override fun createCoupon(couponList: List<Coupon>) {
        val sql = "INSERT INTO coupon (status, is_deleted, created_at, updated_at) VALUES (?, ?, ?, ?)"

        jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                ps.setString(1, couponList[i].status.name)
                ps.setBoolean(2, false)
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()))
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()))
            }

            override fun getBatchSize(): Int = couponList.size
        })
    }
}