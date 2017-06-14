
/*
 * =========================================================================================
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

package kamon.instrumentation.scala.advisor

import io.opentracing.ActiveSpan
import kamon.agent.libs.net.bytebuddy.asm.Advice.{Enter, OnMethodEnter, OnMethodExit, This}
import kamon.util.HasContinuation

/**
  * Advisor for scala.concurrent.impl.CallbackRunnable::run
  * Advisor for scala.concurrent.impl.Future$PromiseCompletingRunnable::run
  */
class RunMethodAdvisor
object RunMethodAdvisor {
  @OnMethodEnter
  def onEnter(@This runnable: HasContinuation): ActiveSpan =
    runnable.continuation.activate()

  @OnMethodExit(onThrowable = classOf[Throwable])
  def onExit(@Enter activeSpan:ActiveSpan): Unit =
    activeSpan.deactivate()
}