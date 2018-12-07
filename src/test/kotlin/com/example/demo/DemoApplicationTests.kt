package com.example.demo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.lang.RuntimeException

@RunWith(SpringRunner::class)
class DemoApplicationTests {

	private val demoApplication = DemoApplication()

    /* Test 1: This should pass but fails */
	@Test
	fun testCombineLatestWithErrorMonoAndEmptyFluxUsingPublishOnScheduler() {

		StepVerifier.create(demoApplication.combineLatestWithScheduledCallableMonoAndFlux(null, emptyList()))
			.expectError(RuntimeException::class.java)
			.verify()

	}

    /* Test 2: This should pass */
    @Test
    fun testCombineLatestWithErrorMonoAndNonEmptyFluxUsingPublishOnScheduler() {

        StepVerifier.create(demoApplication.combineLatestWithScheduledCallableMonoAndFlux(null, listOf(1)))
            .expectError(RuntimeException::class.java)
            .verify()

    }

    /* Test 3: This should pass */
    @Test
    fun testCombineLatestWithErrorMonoAndEmptyFluxUsingDefaultScheduler() {

        StepVerifier.create(demoApplication.combineLatestWithDefaultCallableMonoAndFlux(null, emptyList()))
            .expectError(RuntimeException::class.java)
            .verify()

    }

    /* Test 4: This should pass */
    @Test
    fun testCombineLatestWithErrorMonoAndNonEmptyFluxUsingDefaultScheduler() {

        StepVerifier.create(demoApplication.combineLatestWithDefaultCallableMonoAndFlux(null, listOf(1)))
            .expectError(RuntimeException::class.java)
            .verify()

    }

    /* Test 5: Kind of fix */
    @Test
    fun testCombineLatestWithErrorMonoAndEmptyFluxWithSwitchIfEmptyUsingPublishOnSchedulerr() {

        StepVerifier.create(demoApplication.combineLatestWithScheduledCallableMonoAndFluxWithEmptyCondition(null, emptyList()))
            .expectError(RuntimeException::class.java)
            .verify()

    }

}
