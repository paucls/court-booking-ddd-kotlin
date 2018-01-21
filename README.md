# court-booking-ddd
A sample application using DDD and a Clean Architecture. Implemented in Kotlin and Spring Boot.

## The Domain
The newest startup in town wants to develop a SaaS court booking system. Its focus is on tennis and squash court 
reservations. 

The business goal is to sell tenant subscription plans to organizations: sport clubs, gyms, community centres, etc.

- **Club administrators** manage player memberships, courts, calendar and time slots, bookings. Have access to statistics: booking trends, membership trends, gender and age profile, top active members. Can send direct notifications to members, for example, to communicate cancellations for maintenance. 
- **Staff personnel** have access to view the scheduled Bookings per day, week or month. This helps them to plan their daily work.
- **Members** can create a court booking, update booking time, update booking court, cancel a booking, confirm a booking using its member card.

### Subdomains:
**Booking Scheduling** is the core domain we’ll design it using DDD. Some of its requirements are:
- A Court can not have multiple bookings for same day and time.
- A Member can not have multiple bookings that overlap in time.
- Booking can only be confirmed by the member that created it.
- The minimum amount of time that a court can be booked is 30 minutes.
- Member is notified via email after booking a court.
- When a booking elapses without getting confirmed by a member, system notifies member.

**Billing** sub-domain.
Some of its requirements are:
- Send Invoice to Member automatically when the Booking is confirmed.
- A cancellation fee is applied when a Member cancels a Booking.

**Membership** sub-domain.
For the purpose of this sample application, it is a simple CRUD of Members.

**Facilities** sub-domain.
For the purpose of this sample application, it is a simple CRUD of Locations and Courts.

# References
Docs and articles used to implement this project:
- Entities, Value Objects, Aggregates and Roots. Jimmy Bogard. http://thepaulrayner.com/blog/aggregates-and-entities-in-domain-driven-design/