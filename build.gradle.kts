val kotlinxVersion: String by project
val kotlinxSerializationVersion: String by project
val ktorVersion: String by project

plugins {
    kotlin("multiplatform") version "1.3.61"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.61"
    id("kotlin-dce-js") version "1.3.61"
}

repositories {
    jcenter()
}

kotlin {
    js {
        browser {
            webpackTask {
                sourceMaps = false // Set 'true' if need debug
                report = true // Enable execute tests src/jsTest
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    usePhantomJS()
                }
            }
        }
    }
    sourceSets {
        fun kotlinx(artifact: String) = "org.jetbrains.kotlinx:$artifact:$kotlinxVersion"
        fun kotlinxSerialization(artifact: String) = "org.jetbrains.kotlinx:$artifact:$kotlinxSerializationVersion"
        fun ktor(artifact: String) = "io.ktor:$artifact:$ktorVersion"
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinxSerialization("kotlinx-serialization-runtime-common"))
            }
        }
        val commonTest by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation(kotlinx("kotlinx-coroutines-core-js"))
                implementation(kotlinxSerialization("kotlinx-serialization-runtime-js"))

                implementation(ktor("ktor-client-json-js"))
                implementation(ktor("ktor-client-js"))
            }
        }
        val jsTest by getting {
            dependsOn(jsMain)
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

val PRODUCTION_DIR = "build/production"
tasks {
    named("jsBrowserRun") {
        dependsOn("runDceJsKotlin")
    }
    named("jsBrowserWebpack") {
        dependsOn("runDceJsKotlin")
    }
    named("jsBrowserTest") {
        dependsOn("runDceJsKotlin")
    }
    register("buildProduction", Copy::class.java) {
        setDescription("generate directory $PRODUCTION_DIR")
        dependsOn("jsBrowserWebpack")
        dependsOn("jsBrowserTest")
        from("build/distributions") {
            include("*.js")
        }
        from("src/jsMain/resources")
        into(PRODUCTION_DIR)
    }
    register("runProduction") {
        setDescription("run simple web server with content: $PRODUCTION_DIR")
        dependsOn("buildProduction")
        doLast {
            val port = 8081
            SimpleHttpFileServerFactory().start(file(PRODUCTION_DIR), port)
            println("Open http://localhost:$port/index.html")
            Thread.sleep(Long.MAX_VALUE)
        }
    }
    named("build") {
        dependsOn("buildProduction")
    }
}
