package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.time.LocalTime
import kotlin.test.assertFailsWith

class TimeRangeTest : Spek({

    describe("initialization") {

        it("start must be earlier than end") {
            assertFailsWith<ScheduleExceptions.InvalidTimeInterval> {
                TimeRange(LocalTime.of(11, 0), LocalTime.of(10, 0))
            }
        }

    }

    describe("overlapsWith") {

        it("should return true if the two time ranges overlap") {
            val tenToHalfTen = TimeRange(LocalTime.of(10, 0), LocalTime.of(10, 30))
            val halfTenToEleven = TimeRange(LocalTime.of(10, 30), LocalTime.of(11, 0))
            val tenToEleven = TimeRange(LocalTime.of(10, 0), LocalTime.of(11, 0))

            assertThat(tenToHalfTen.overlapsWith(tenToEleven)).isTrue()
            assertThat(tenToEleven.overlapsWith(tenToHalfTen)).isTrue()
            assertThat(halfTenToEleven.overlapsWith(tenToEleven)).isTrue()
            assertThat(tenToEleven.overlapsWith(halfTenToEleven)).isTrue()
        }

        it("should return false if the two time ranges do not overlap") {
            val tenToHalfTen = TimeRange(LocalTime.of(10, 0), LocalTime.of(10, 30))
            val halfTenToEleven = TimeRange(LocalTime.of(10, 30), LocalTime.of(11, 0))

            assertThat(tenToHalfTen.overlapsWith(halfTenToEleven)).isFalse()
        }

    }
})