package org.xarcher.jfxgit

import java.io.File

import scala.util.Try
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextArea, TextField}
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
  val field = VarModel.empty[TextArea]
  val initButton = VarModel.empty[Button]
  val commitButton = VarModel.empty[Button]
  val statusButton = VarModel.empty[Button]
  val statusBox = VarModel.empty[VBox]
  val addAllButton = VarModel.empty[Button]
  val defaultWidth = 454
  def folderName: File = new File(parameters.raw(0))

  def rewriteList = {
    statusBox.get.children = {
      def aa: List[HBox] =  GitDeal.genTextField(folderName, () => {
        statusBox.get.children = aa
      }: Unit)
      aa
    }
  }

  stage = stageS setTo new JFXApp.PrimaryStage {
    title.value = "git 死你"
    height = 600
    width = 600

    scene = sceneS setTo new Scene {
      height_=(900)
      width_=(800)
      content = parentBox setTo new VBox {
        fillWidth = true
        children = List(
          field setTo new TextArea {
            text = ""
            prefWidth = 600
          },
          /*initButton setTo new Button {
            text = "新建"
            handleEvent(ActionEvent.Action) {
              event: ActionEvent =>
                GitDeal.init(folderName)
            }
          },*/
          commitButton setTo new Button {
            text = "提交"
            handleEvent(ActionEvent.Action) {
              event: ActionEvent =>
                GitDeal.commit(folderName)(field.get.text.value)
                stageS.get.close()
            }
          },
          addAllButton setTo new Button {
            text = "add all"
            handleEvent(ActionEvent.Action) {
              event: ActionEvent =>
                GitDeal.addAll(folderName)
                rewriteList
            }
          },
          statusButton setTo new Button {
            text = "status"
            handleEvent(ActionEvent.Action) {
              event: ActionEvent =>
                rewriteList
            }
          },
          statusBox setTo new VBox {
            children = Nil
          }
        )
      }
    }
  }

}