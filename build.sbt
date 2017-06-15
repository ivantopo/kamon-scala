/* =========================================================================================
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

val kamonCore         = "io.kamon"    %% "kamon-core"                   % "1.0.0-alpha1-05732d8693910248338744fa587bc4bc38ffb1ed"
val scalazConcurrent  = "org.scalaz"  %% "scalaz-concurrent"            % "7.2.8"

//kamon-agent
val agentScala        = "io.kamon"    %% "agent-scala-extension"        % "0.0.3-experimental"
val kamonAgent        = "io.kamon"    %  "kamon-agent"                  % "0.0.3-experimental"

lazy val root = (project in file("."))
  .settings(name := "kamon-scala")
  .settings(aspectJSettings: _*)
  .enablePlugins(JavaAgent)
  .settings(javaAgents += "org.aspectj" % "aspectjweaver" % "1.8.10"  % "compile;test")
//  .settings(javaAgents += "io.kamon"    % "kamon-agent"   % "0.0.3-experimental"  % "test")
  .settings(
      libraryDependencies ++=
        compileScope(kamonCore, agentScala) ++
        providedScope(aspectJ, kamonAgent) ++
        optionalScope(scalazConcurrent, twitterDependency("core").value) ++
        testScope(scalatest, logbackClassic))

def twitterDependency(moduleName: String) = Def.setting {
  scalaBinaryVersion.value match {
    case "2.10"           => "com.twitter" %% s"util-$moduleName" % "6.34.0"
    case "2.11" | "2.12"  => "com.twitter" %% s"util-$moduleName" % "6.40.0"
  }
}
