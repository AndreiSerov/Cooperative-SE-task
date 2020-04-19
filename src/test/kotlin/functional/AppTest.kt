package functional

import com.dinosaur.app.App
import org.spekframework.spek2.Spek

object AppTest : Spek({
    var app = App()
    beforeEachTest {
        app = App()
    }

})
