plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ibooks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ibooks"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary=true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

//    repositories {
//        mavenCentral()
//    }

}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    implementation ("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation ("androidx.navigation:navigation-fragment:2.7.6")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("androidx.navigation:navigation-ui:2.7.6")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.hbb20:ccp:2.7.3")
    implementation ("androidx.navigation:navigation-ui:2.7.6")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.retrofit2:retrofit:2.6.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation ("androidx.navigation:navigation-fragment:2.7.6")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.navigation:navigation-ui:2.7.6")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("com.hbb20:ccp:2.7.3")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

}