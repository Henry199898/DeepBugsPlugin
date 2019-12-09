import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.intellij.tasks.BuildSearchableOptionsTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import tanvd.kosogor.defaults.configureIdea

group = "org.jetbrains.research.deepbugs"
version = "0.3"


plugins {
    id("tanvd.kosogor") version "1.0.7" apply true
    id("io.gitlab.arturbosch.detekt") version ("1.1.1") apply false
    id("org.jetbrains.intellij") version "0.4.13" apply true
    kotlin("jvm") version "1.3.61" apply true
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.61" apply true
}

allprojects {
    repositories {
        jcenter()
        maven("https://dl.bintray.com/mipt-npm/scientifik")
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlin.plugin.serialization")
        plugin("org.jetbrains.intellij")
        plugin("tanvd.kosogor")
        plugin("io.gitlab.arturbosch.detekt")
    }

    tasks.withType<BuildSearchableOptionsTask>().forEach { it.enabled = false }

    configureIdea {
        exclude += file("build")
    }

    intellij {
        sandboxDirectory = File(rootProject.projectDir, "build/${project.name}/idea-sandbox").canonicalPath
    }

    detekt {
        parallel = true
        failFast = false
        config = files(File(rootProject.projectDir, "buildScripts/detekt/detekt.yml"))
        reports {
            xml {
                enabled = false
            }
            html {
                enabled = false
            }
        }
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx", "kotlinx-serialization-runtime", "0.14.0")
    }

    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            languageVersion = "1.3"
            apiVersion = "1.3"
        }
    }
}
