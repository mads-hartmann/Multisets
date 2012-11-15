import sbt._
import Keys._
import Process._
import java.io.File

object Multisets extends Build {

 /* tasks and settings */
 val javaCommand = TaskKey[String](
   "java-command",
   "Creates a java vm command for launching a process.")

 val javaCommandSetting = javaCommand <<= (
   dependencyClasspath in Compile,
   artifactPath in (Compile, packageBin),
   artifactPath in (Test, packageBin),
   packageBin in Compile,
   packageBin in Test
 ) map {
   (dp, jar, testjar, pbc, pbt) =>
   val javacommand = "java -Xmx2048m -Xms2048m -verbose:gc -server -cp %s:%s:%s:%s:%s".format(
     dp.map(_.data).mkString(":"), jar, testjar, pbc, pbt)
   javacommand
 }

 val runBenchTask = InputKey[Unit]("runbench", "Runs the benchmark.") <<= inputTask {
   (argTask: TaskKey[Seq[String]]) => (argTask, javaCommand) map { (args, jc) =>
     val javacommand = jc
     val comm = javacommand + " " + args.mkString(" ")
     println("Executing: " + comm)
     comm!
   }
 }

 /* projects */
 lazy val project = Project(
   "Multisets",
   file("."),
   settings = Defaults.defaultSettings ++ Seq(runBenchTask, javaCommandSetting)
 ) dependsOn ()

}