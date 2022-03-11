package moduledependencies

val androidTestLibraries = arrayListOf<String>().apply {
    add(DependencyContainer.jUnitExt)
    add(DependencyContainer.espressoCore)
}