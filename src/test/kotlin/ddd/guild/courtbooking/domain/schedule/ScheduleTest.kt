package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDate

private const val SCHEDULE_ID = "schedule-id"
private const val LOCATION_ID = "location-id"

@RunWith(JUnitPlatform::class)
class ScheduleTest : Spek({

    it("can create schedule for a day") {
        val today = LocalDate.now()
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, today)

        assertThat(schedule.id).isEqualTo(SCHEDULE_ID)
        assertThat(schedule.locationId).isEqualTo(LOCATION_ID)
        assertThat(schedule.day).isEqualTo(today)
    }

})