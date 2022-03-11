package moduledependencies

val databaseLibraries = arrayListOf<String>().apply {
    add(DependencyContainer.gson)
    add(DependencyContainer.room)
    add(DependencyContainer.coroutinesCore)
    add(DependencyContainer.coroutinesAndroid)
    add(DependencyContainer.hiltAndroid)
}

val databaseKaptLibraries = arrayListOf<String>().apply {
    add(DependencyContainer.roomCompiler)
    add(DependencyContainer.hiltCompiler)
}