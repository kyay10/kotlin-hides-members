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

package io.github.kyay10.kotlinhidesmembers

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.resolve.descriptorUtil.HIDES_MEMBERS_NAME_LIST
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.jvm.javaField

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
class HidesMembersPluginRegistrar : CompilerPluginRegistrar() {
  override val supportsK2: Boolean
    get() = true

  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    ::HIDES_MEMBERS_NAME_LIST
      .javaField
      ?.forceAccessible()
      ?.set(null, UniversalSet(HIDES_MEMBERS_NAME_LIST))
  }
}

class UniversalSet<E>(private val originalSet: Set<E>) : Set<E> by originalSet {
  override fun contains(element: E): Boolean = true

  override fun containsAll(elements: Collection<E>): Boolean = true

  override fun toString(): String {
    return "UniversalSet($originalSet)"
  }
}

fun Field.forceAccessible() = apply {
  isAccessible = true
  val modifiersField = Field::class.java.getDeclaredField("modifiers").apply { isAccessible = true }
  modifiersField.setInt(this, modifiers and Modifier.FINAL.inv())
}
