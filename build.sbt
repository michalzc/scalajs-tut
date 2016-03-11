
name := "Scala.js tutorial"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0"
)
