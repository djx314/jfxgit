import org.xarcher.sbt.CustomSettings
import org.xarcher.sbt.Dependencies
import org.xarcher.sbt.Helpers._

import sbt._
import sbt.Keys._

val gitInit = taskKey[String]("miao")
val autoGit = taskKey[String]("wang")
val jgitVersion = "4.4.0.201606070830-r"

lazy val jfxgit = (project in file("."))
.settings(
  name := "jfxgit",
  version := "0.0.1"
).settings(

  libraryDependencies ++= Dependencies.ammoniteRepl,
  //libraryDependencies += "net.coobird" % "thumbnailator" % "0.4.8",
  libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10",
  //libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2",
  libraryDependencies ++= List(
    "org.eclipse.jgit" % "org.eclipse.jgit",
    "org.eclipse.jgit" % "org.eclipse.jgit.pgm",
    "org.eclipse.jgit" % "org.eclipse.jgit.http.server",
    "org.eclipse.jgit" % "org.eclipse.jgit.ui",
    "org.eclipse.jgit" % "org.eclipse.jgit.junit"
  ) map (
    _ % jgitVersion
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
  ),
  fork := true,

  {
    if (scala.util.Properties.isWin)
      initialCommands in (Test, console) += s"""ammonite.repl.Main.run("repl.frontEnd() = ammonite.repl.frontend.FrontEnd.JLineWindows");"""
    else
      initialCommands in (Test, console) += s"""ammonite.repl.Main.run("");"""
  },

  gitInit := {

    val runtime = java.lang.Runtime.getRuntime

    import scala.io.Source
    if (scala.util.Properties.isWin) {
      val commandLine = Source.fromFile("./gitUpdate").getLines.map(s => s.replaceAll("\\r\\n", "")).mkString(" & ")
      val process = runtime.exec(List("cmd", "/c", commandLine).toArray)
      execCommonLine(process)
    } else {
      val commandLine = Source.fromFile("./gitUpdate").getLines.map(s => s.replaceAll("\\r\\n", "")).mkString(" ; ")
      val process = runtime.exec(List("sh", "-c", commandLine).toArray)
      execCommonLine(process)
    }
    "执行 git 初始化操作成功"

  },

  autoGit := {
    org.xarcher.jfxgit.Jfxgit.main(Array("./"))
    "提交完毕"
  }

)
.settings(CustomSettings.customSettings: _*)
.enablePlugins(JDKPackagerPlugin)
.enablePlugins(WindowsPlugin)