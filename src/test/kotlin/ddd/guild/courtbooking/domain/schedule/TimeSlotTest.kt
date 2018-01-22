package ddd.guild.courtbooking.domain.schedule

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalTime
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
class TimeSlotTest : Spek({

    it("start time must be earlier than end time") {
        assertFailsWith<ScheduleExceptions.InvalidTimeInterval> {
            TimeSlot(LocalTime.of(11, 0), LocalTime.of(10, 0))
        }
    }

    it("duration can not be less than 30 minutes") {
        assertFailsWith<ScheduleExceptions.InvalidDuration> {
            TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 15))
        }
    }

})