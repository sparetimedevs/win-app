# Who is next? - Multiplatform project

## Kotlin Common
All common code is located in the `/src/commonMain/` package.

## Kotlin/JS
Run locally:

`$ ./gradlew jsBrowserRun`
http://localhost:8080
or 
`$ ./gradlew jsBrowserRun -t` for life code sync

Run tests:

`$ ./gradlew clean`

`$ ./gradlew jsBrowserTest`

build production code:

`$ ./gradlew buildProduction`

run production code locally:

`$ ./run.sh`
