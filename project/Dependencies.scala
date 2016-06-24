package org.xarcher.sbt

import sbt._
import sbt.Keys._

object Dependencies {

  //repl
  val ammoniteRepl = Seq(
    "com.lihaoyi" % "ammonite-repl" % "0.6.2" % "test" cross CrossVersion.full
  )

}