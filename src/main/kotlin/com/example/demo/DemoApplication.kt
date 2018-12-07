package com.example.demo

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors
import java.util.function.BiFunction

class DemoApplication {

    fun combineLatestWithScheduledCallableMonoAndFlux(monoValue: Int?, fluxList: List<Int>): Mono<Unit> {

        return Flux.combineLatest(returnScheduledCallableMonoWithValue(monoValue), fluxFromList(fluxList), BiFunction<Int, Int, Int> { t, u -> t + u })
            .collectList()
            .map { Unit }

    }

    fun combineLatestWithDefaultCallableMonoAndFlux(monoValue: Int?, fluxList: List<Int>): Mono<Unit> {

        return Flux.combineLatest(returnDefaultCallableMonoWithValue(monoValue), fluxFromList(fluxList), BiFunction<Int, Int, Int> { t, u -> t + u })
            .collectList()
            .map { Unit }

    }

    fun combineLatestWithScheduledCallableMonoAndFluxWithEmptyCondition(monoValue: Int?, fluxList: List<Int>): Mono<Unit> {

        return Flux.combineLatest(returnScheduledCallableMonoWithValue(monoValue), fluxFromListSwitchIfEmpty(fluxList), BiFunction<Int, Int, Int> { t, u -> t + u })
            .collectList()
            .map { Unit }

    }

    /* Publishers */

    private fun returnScheduledCallableMonoWithValue(value: Int?): Mono<Int> {

        val scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(10))

        return Mono.fromCallable<Int> {
            Thread.sleep(10) //represents a DB call
            value
        }
            .publishOn(scheduler)
            .switchIfEmpty(Mono.error<Int>(RuntimeException("We failed")))
    }

    private fun returnDefaultCallableMonoWithValue(value: Int?): Mono<Int> {

        return Mono.fromCallable<Int> {
            Thread.sleep(10) //represents a DB call
            value
        }
            .switchIfEmpty(Mono.error<Int>(RuntimeException("We failed")))
    }


    private fun fluxFromList(fluxList: List<Int>): Flux<Int> {
        return fluxList.toFlux()
    }

    private fun fluxFromListSwitchIfEmpty(fluxList: List<Int>): Flux<Int> {
        return fluxList.toFlux()
            .switchIfEmpty(Mono.error<Int>(RuntimeException("We failed")))
    }

}