@file:Suppress("unused")

package moduledependencies

val debugDependencies = arrayListOf<String>().apply {
    add(DependencyContainer.leakcanary)
}