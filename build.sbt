
name := "Scala.js tutorial"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.0",

  //test deps
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)

skip in packageJSDependencies := false
jsDependencies ++= Seq(
  "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  RuntimeDOM
)

testFrameworks += new TestFramework("utest.runner.Framework")
