package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.time.LocalTime
import kotlin.test.assertFailsWith

class BookingTimeTest : Spek({

    describe("initialization") {

        it("start must be earlier than end") {
            assertFailsWith<ScheduleExceptions.InvalidTimeInterval> {
                BookingTime(LocalTime.of(11, 0), LocalTime.of(10, 0))
            }
        }

        it("duration can not be less than 30 minutes") {
            assertFailsWith<ScheduleExceptions.InvalidDuration> {
                BookingTime(LocalTime.of(10, 0), LocalTime.of(10, 29))
            }
        }

    }

    describe("overlapsWith") {

        it("should return true if the two booking times overlap") {
            val tenToHalfTen = BookingTime(LocalTime.of(10, 0), LocalTime.of(10, 30))
            val halfTenToEleven = BookingTime(LocalTime.of(10, 30), LocalTime.of(11, 0))
            val tenToEleven = BookingTime(LocalTime.of(10, 0), LocalTime.of(11, 0))

            assertThat(tenToHalfTen.overlapsWith(tenToEleven)).isTrue()
            assertThat(tenToEleven.overlapsWith(tenToHalfTen)).isTrue()
            assertThat(halfTenToEleven.overlapsWith(tenToEleven)).isTrue()
            assertThat(tenToEleven.overlapsWith(halfTenToEleven)).isTrue()
        }

        it("should return false if the two booking times do not overlap") {
            val tenToHalfTen = BookingTime(LocalTime.of(10, 0), LocalTime.of(10, 30))
            val halfTenToEleven = BookingTime(LocalTime.of(10, 30), LocalTime.of(11, 0))

            assertThat(tenToHalfTen.overlapsWith(halfTenToEleven)).isFalse()
        }

    }

})