object AppConfig {
    // Version Control
    const val appVersionCode = 1
    const val appVersionName = "1.0.0"

    // Build Control
    const val compileSdkVersion = 31
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    //BuildTypes
    const val releaseBuild = "release"
    const val debugBuild = "debug"

    // Release Signing Configs (Test purpose only)
    const val releaseKeyStoreFile = "NoteApp.keystore"
    const val releaseConfigName = "release"
    const val releaseAlias = "noteAppKeyAlias"
    const val releasePass = "!Note@123"

    // Modules
    const val DATABASE_MODULE = ":database"
}