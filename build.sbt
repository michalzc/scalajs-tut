import sbt._
import sbt.Keys._

name := "Contact List Example"

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val root = project.in(file("."))
  .aggregate(`clist-client`, `clist-server`, `clist-domain`)
  .disablePlugins(sbtassembly.AssemblyPlugin)


lazy val `clist-client` = project.in(file("clist-client"))
  .dependsOn(`clist-domain` % "test->test;compile->compile" )
  .enablePlugins(ScalaJSPlugin)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0",
      "com.github.japgolly.scalajs-react" %%% "core" % "0.11.0",
      "org.scalaz" %%% "scalaz-core" % "7.2.2",
      "biz.enef" %%% "slogging" % "0.4.0",

      //test deps
      "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.0.1"
        /             "react-with-addons.js"
        minified      "react-with-addons.min.js"
        commonJSName  "React",
      "org.webjars.bower" % "react" % "15.0.1"
        /             "react-dom.js"
        minified      "react-dom.min.js"
        dependsOn     "react-with-addons.js"
        commonJSName  "ReactDOM",
        RuntimeDOM
    ),
    skip in packageJSDependencies := false,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalaJSStage in Global := FastOptStage
  )

lazy val `clist-server` = project.in(file("clist-server"))
  .dependsOn(`clist-domain` % "test->test;compile->compile")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",

      //test
      "com.typesafe.akka" %% "akka-http-testkit" % "2.4.2" % "test",
      "org.specs2" %% "specs2-core" % "3.7.2" % "test"
    ),
    (resources in Compile) += (fastOptJS in (`clist-client`, Compile)).value.data,
    (resources in Compile) += (packageJSDependencies in (`clist-client`, Compile)).value,
    (resources in Compile) <++= (resources in (`clist-client`, Compile))
  )

lazy val `clist-domain` = project.in(file("clist-domain"))
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(commonSettings: _*)
