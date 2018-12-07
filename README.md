# reactor-bug
This repo demonstrates inconsistent behaviour with the `combineLatest` operator when using a publisher `fromCallable` with `publishOn(Scheduler)`. Tests demonstrating the behaviour can be found in `DemoApplicationTests.kt`

The tests are attempting to use `Flux.combineLatest()` combining a `Mono` and a `Flux`. These publishers are performing database access which are wrapped in `Callable`. This repo has used `Thread.sleep(10)` to simulate the database access. There is a possibility of the `Mono` publisher returning an `Mono.error()` and there is a possibility of the `Flux` being empty. If the `Mono.fromCallable()` utilises the `publishOn(Scheduler)` operator then the `Error` is not bubbled up to the subscriber. If the `publishOn` operator is removed, then the `Error` correctly bubbles to the subscriber.

There is currently one workaround if an empty `Flux` can be considered an error, but this might not always be the case.

## Test 1 - Potential Bug
Use `Flux.combineLatest` with a `Mono` that returns an Error and utilizes `publishOn(Scheduler)` and with an empty `Flux`. Expect the error to be caught by the subscriber but instead `onNext` is called.

## Test 2
Use `Flux.combineLatest` with a `Mono` that returns an Error and utilizes `publishOn(Scheduler)` and with an non empty `Flux`. Passes.

## Test 3
Use `Flux.combineLatest` with a `Mono` that returns an Error with an empty `Flux`. Passes.

## Test 4
Use `Flux.combineLatest` with a `Mono` that returns an Error with an empty `Flux`. Passes.

## Test 5
Use `Flux.combineLatest` with a `Mono` that returns an Error and utilizes `publishOn(Scheduler)` and with an empty `Flux` that has a `switchIfEmpty` error. Passes.