plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization")
  kotlin("kapt")
  id("io.github.985892345.MavenPublisher")
}

android {
  namespace = "com.ndhzs.widget"
  compileSdk = 34

  defaultConfig {
    minSdk = 24
    targetSdk = 34

//    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
//      isMinifyEnabled = true
//      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.7"
  }
}

kotlin {
  jvmToolchain(8)
}

kapt {
  arguments {
    arg("room.schemaLocation", "${project.projectDir}/schemas") // room 的架构导出目录
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.15.0")
  implementation("androidx.appcompat:appcompat:1.7.0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

  val room_version = "2.6.1"
  implementation("androidx.room:room-runtime:$room_version")
  implementation("androidx.room:room-ktx:$room_version")
  kapt("androidx.room:room-compiler:$room_version") // ksp 后面再迁移

  // Glance 用的残缺 Compose，虽然声明式很适合小组件，但功能还很不完善，目前只是简单的小组件使用 Glance 实现
  implementation("androidx.glance:glance-appwidget:1.1.1")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

  implementation("androidx.work:work-runtime-ktx:2.10.0")
}

publisher {
  masterDeveloper = DeveloperInformation(
    githubName = "985892345",
    email = "guo985892345@formail.com"
  )
  description = "CQUPT桌面课表小组件"
}