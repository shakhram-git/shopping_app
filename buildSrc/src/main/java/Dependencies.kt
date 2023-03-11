object Versions {
    const val core = "1.9.0"
    const val appcompat = "1.6.1"
    const val androidMaterial = "1.8.0"
    const val constraintLayout = "2.1.4"
    const val testJunit = "4.13.2"
    const val androidTestJunit = "1.1.5"
    const val androidTestEspresso = "3.5.1"
    const val hilt = "2.44"
    const val retrofit = "2.9.0"
    const val moshi = "1.14.0"
    const val room = "2.5.0"
    const val coroutines = "1.3.9"
    const val viewPager = "1.0.0"
    const val fragment = "1.5.5"
    const val adapterDelegates = "4.3.2"
    const val glide = "4.14.2"
    const val preferences = "1.1.0-alpha01"
}

object Deps {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.testJunit}"
}

object AndroidTestDeps {
    const val junit = "androidx.test.ext:junit:${Versions.androidTestJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}

object HiltDagger {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
}

object Retrofit {
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
}

object Moshi {
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
}

object Room {
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}

object Coroutines {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object ViewPager {
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager}"
}

object AdapterDelegates {
    const val adapterDelegates =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.adapterDelegates}"
    const val adapterDelegatesViewBinding =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.adapterDelegates}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Preferences {
    const val prefsDataStore = "androidx.datastore:datastore-preferences:${Versions.preferences}"
}
