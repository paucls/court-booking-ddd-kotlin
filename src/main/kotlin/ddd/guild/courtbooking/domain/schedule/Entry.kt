package ddd.guild.courtbooking.domain.schedule

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Entry(
        @Id
        val id: String,
        @Embedded
        val time: TimeRange,
        val description: String
) {
    // Used by JPA
    @Column(name = "schedule_id")
    private lateinit var scheduleId: String
}