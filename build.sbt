name := "multisets"

version := "0.1"

organization := "com.sidewayscoding"

scalaVersion := "2.9.2"

resolvers ++= Seq(
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

scalacOptions ++= Seq(
  "-deprecation"
)

libraryDependencies ++= Seq( 
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
  "org.specs2" %% "specs2" % "1.12.1" % "test"
)

initialCommands := """
  import com.sidewayscoding._
  import com.sidewayscoding.immutable._"""

seq(ScctPlugin.instrumentSettings : _*)
