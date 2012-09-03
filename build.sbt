name := "multisets"

version := "0.1"

organization := "com.sidewayscoding"

scalaVersion := "2.10"

resolvers ++= Seq(
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq( 
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
)