import sbt._
import sbt.Keys._

name := "Let's go there"

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val root = project.in(file("."))
  .aggregate(`lgt-client`, `lgt-server`, `lgt-domain`)
  .disablePlugins(sbtassembly.AssemblyPlugin)


lazy val `lgt-client` = project.in(file("lgt-client"))
  .dependsOn(`lgt-domain` % ("test->test;compile->compile"))
  .enablePlugins(ScalaJSPlugin)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.0",

      //test deps
      "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
    ),
    jsDependencies ++= Seq(
      "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
      RuntimeDOM
    ),
    skip in packageJSDependencies := false,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalaJSStage in Global := FastOptStage
  )

lazy val `lgt-server` = project.in(file("lgt-server"))
  .dependsOn(`lgt-domain` % "test->test;compile->compile")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",

      //test
      "com.typesafe.akka" %% "akka-http-testkit" % "2.4.2" % "test",
      "org.specs2" %% "specs2-core" % "3.7.2" % "test"
    ),
    (resources in Compile) += (fastOptJS in (`lgt-client`, Compile)).value.data,
    (resources in Compile) += (packageJSDependencies in (`lgt-client`, Compile)).value,
    (resources in Compile) <++= (resources in (`lgt-client`, Compile))
  )

lazy val `lgt-domain` = project.in(file("lgt-domain"))
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(commonSettings: _*)
