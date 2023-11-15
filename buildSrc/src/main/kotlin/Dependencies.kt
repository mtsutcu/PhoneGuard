import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies{
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNav = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNav}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_savestate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_compose_runtime = "androidx.compose.runtime:runtime-livedata:1.5.4"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_compose ="androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"

    const val navigation_compose = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigation_runtime_ktx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"

}


fun DependencyHandler.hilt(){
    implementation(Dependencies.hiltNav)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
}

fun DependencyHandler.room(){
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
}
fun DependencyHandler.lifecycle(){
    implementation(Dependencies.lifecycle_viewmodel)
    implementation(Dependencies.lifecycle_viewmodel_savestate)
    implementation(Dependencies.lifecycle_livedata)
    implementation(Dependencies.lifecycle_compose_runtime)
    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_viewmodel_compose)
}
fun DependencyHandler.coroutines(){
    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)
}

fun DependencyHandler.navigation(){
    implementation(Dependencies.navigation_compose)
    implementation(Dependencies.navigation_runtime_ktx)
    implementation(Dependencies.navigation_ui_ktx)
}