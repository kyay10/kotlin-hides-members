/*
 * Copyright (C) 2020 Brian Norman
 * Copyright (C) 2021 Youssef Shoaib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package io.github.kyay10.kotlinhidesmembers

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class HidesMembersGradlePlugin : KotlinCompilerPluginSupportPlugin {
  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

  override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

  override fun getPluginArtifact(): SubpluginArtifact =
      SubpluginArtifact(
          groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
          artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
          version = BuildConfig.KOTLIN_PLUGIN_VERSION)

  override fun applyToCompilation(
      kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    kotlinCompilation.dependencies {
      implementation(BuildConfig.PRELUDE_LIBRARY)
    }
    return kotlinCompilation.target.project.provider { listOf() }
  }
}
