import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.konfig)
}


val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

buildkonfig {
    packageName = "com.mlombardi.marvelcharacters"

    defaultConfigs {
        val privateKey = localProperties.getProperty("private_key") ?: "private_key"
        val publicKey = localProperties.getProperty("public_key") ?: "public_key"

        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "private_key",
            privateKey
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "public_key",
            publicKey
        )
    }
}
kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    configurations.all {
        resolutionStrategy {
            // Compose UI Core
            force("androidx.compose.ui:ui:1.7.4")
            force("androidx.compose.ui:ui-android:1.7.4")
            force("androidx.compose.ui:ui-text:1.7.4")
            force("androidx.compose.ui:ui-text-android:1.7.4")
            force("androidx.compose.ui:ui-graphics:1.7.4")
            force("androidx.compose.ui:ui-graphics-android:1.7.4")
            force("androidx.compose.ui:ui-tooling:1.7.4")
            force("androidx.compose.ui:ui-tooling-android:1.7.4")
            force("androidx.compose.ui:ui-tooling-data:1.7.4")
            force("androidx.compose.ui:ui-tooling-data-android:1.7.4")

            // Compose Runtime
            force("androidx.compose.runtime:runtime:1.7.4")
            force("androidx.compose.runtime:runtime-saveable:1.7.4")
            force("androidx.compose.runtime:runtime-saveable-android:1.7.4")

            // Compose Foundation & Animation
            force("androidx.compose.foundation:foundation:1.7.4")
            force("androidx.compose.foundation:foundation-android:1.7.4")
            force("androidx.compose.foundation:foundation-layout-android:1.7.4")
            force("androidx.compose.animation:animation:1.7.4")
            force("androidx.compose.animation:animation-core:1.7.4")
            force("androidx.compose.animation:animation-core-android:1.7.4")

            // Material
            force("androidx.compose.material3:material3:1.2.1")

            // Lifecycle Compose (keep en 2.8.x for SDK 34)
            force("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")
            force("androidx.lifecycle:lifecycle-runtime-compose-android:2.8.3")
            force("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
            force("androidx.lifecycle:lifecycle-viewmodel-compose-android:2.8.3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(compose.components.uiToolingPreview)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.okio)
            api(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.coil3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        dependencies {
            ksp(libs.androidx.room.compiler)
        }
    }
}

android {
    namespace = "com.mlombardi.marvelcharacters"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mlombardi.marvelcharacters"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

