package moduledependencies

val appLibraries = arrayListOf<String>().apply {
    add(DependencyContainer.coreKtx)
    add(DependencyContainer.appCompat)
    add(DependencyContainer.fragment)
    add(DependencyContainer.activity)
    add(DependencyContainer.constraintLayout)
    add(DependencyContainer.recyclerview)
    add(DependencyContainer.lifecycleExtensions)
    add(DependencyContainer.lifecycleViewmodelKtx)
    add(DependencyContainer.lifecycleLivedataKtx)
    add(DependencyContainer.googleMaterial)
    add(DependencyContainer.gson)
    add(DependencyContainer.coroutinesCore)
    add(DependencyContainer.coroutinesAndroid)
    add(DependencyContainer.glide)
    add(DependencyContainer.materialDialogsCore)
    add(DependencyContainer.materialDialogsLifecycle)
    add(DependencyContainer.materialDialogsInput)
    add(DependencyContainer.hiltAndroid)
    add(DependencyContainer.worker)
    add(DependencyContainer.room)
}

val appKaptLibraries = arrayListOf<String>().apply {
    add(DependencyContainer.lifecycleCommonJava8)
    add(DependencyContainer.lifecycleRuntimeKtx)
    add(DependencyContainer.lifecycleCompiler)
    add(DependencyContainer.glideCompiler)
    add(DependencyContainer.hiltCompiler)
    add(DependencyContainer.roomCompiler)
}