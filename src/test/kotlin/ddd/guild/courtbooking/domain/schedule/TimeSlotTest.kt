package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalTime
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
class TimeSlotTest : Spek({

    describe("initialization") {

        it("start time must be earlier than end time") {
            assertFailsWith<ScheduleExceptions.InvalidTimeInterval> {
                TimeSlot(LocalTime.of(11, 0), LocalTime.of(10, 0))
            }
        }

        it("duration can not be less than 40 minutes") {
            assertFailsWith<ScheduleExceptions.InvalidDuration> {
                TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 39))
            }
        }

    }

    describe("overlapsWith") {

        it("should return true if the two time slots overlap") {
            val tenToTenFortySlot = TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 40))
            val tenFortyToElevenTwentySlot = TimeSlot(LocalTime.of(10, 40), LocalTime.of(11, 20))
            val tenToElevenTwentySlot = TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 20))

            assertThat(tenToTenFortySlot.overlapsWith(tenToElevenTwentySlot)).isTrue()
            assertThat(tenToElevenTwentySlot.overlapsWith(tenToTenFortySlot)).isTrue()
            assertThat(tenFortyToElevenTwentySlot.overlapsWith(tenToElevenTwentySlot)).isTrue()
            assertThat(tenToElevenTwentySlot.overlapsWith(tenFortyToElevenTwentySlot)).isTrue()
        }

        it("should return false if the two time slots do not overlap") {
            val tenToElevenSlot = TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 40))
            val halfTenToElevenSlot = TimeSlot(LocalTime.of(10, 40), LocalTime.of(11, 20))

            assertThat(tenToElevenSlot.overlapsWith(halfTenToElevenSlot)).isFalse()
        }

    }

})