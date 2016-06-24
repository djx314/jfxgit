package org.xarcher.xPhoto

import scala.util.Try
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, CheckBox, Label, TextField}
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout._

object Jfxgit extends JFXApp {

  object VarModel {

    trait AbsVar[T] {
      var model: T = _
      def setTo(model1: T): T = {
        model = model1
        model
      }
      def get: T = model
    }

    def empty[T] = new AbsVar[T] { }

  }

  val stageS = VarModel.empty[JFXApp.PrimaryStage]
  val sceneS = VarModel.empty[Scene]
  val parentBox = VarModel.empty[VBox]
  val field = VarModel.empty[TextField]
  val commitButton = VarModel.empty[Button]
  val defaultWidth = 454

  stage = stageS setTo new JFXApp.PrimaryStage {
    title.value = "渣渣 git 死你"
    height = 600
    width = 600

    scene = sceneS setTo new Scene {
      content = parentBox setTo new VBox {
        fillWidth = true
        children = List(
          field setTo new TextField {
            text = "喵喵喵"
          },
          commitButton setTo new Button {
            text = "提交"
          }
        )
      }
    }
  }

}