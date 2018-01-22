package ddd.guild.courtbooking

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith


@RunWith(JUnitPlatform::class)
class SpekTest : Spek({
    beforeEachTest { println( "before test") }
    given("the context") {
        on("an action") {
            it("should satisfy these conditions") {
                assertThat(true).isTrue()
            }
        }
    }
})