package org.xarcher.jfxgit

import java.io.File

import org.eclipse.jgit.api.{Git, Status}
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.IndexDiff.StageState

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.HBox
import scalafx.Includes._

object GitDeal {

  def init(destory: File): Unit = {
    Git.init().setDirectory(destory).call()
  }

  def commit(destory: File)(message: String): Unit = {
    Git.open(destory).commit().setMessage(message).call()
  }

  def addAll(destory: File): Unit = {
    Git.open(destory).add().addFilepattern(".").call()
  }

  case class GitStatus(
    added: Set[String],
    changed: Set[String],
    conflicting: Set[String],
    conflictingStageState: Map[String, StageState],
    ignoredNotInIndex: Set[String],
    missing: Set[String],
    modified: Set[String],
    removed: Set[String],
    uncommittedChanges: Set[String],
    untracked: Set[String]//,
    //untrackedFolders: Set[String]
  )
  object GitStatus {

    def from(status: Status) = {
      def genToSet[E](set: java.util.Set[E]): Set[E] = {
        scala.collection.JavaConversions.asScalaSet(set).toSet
      }
      GitStatus(
        added = genToSet(status.getAdded),
        changed = genToSet(status.getChanged),
        conflicting = genToSet(status.getConflicting),
        conflictingStageState = scala.collection.JavaConversions.mapAsScalaMap(status.getConflictingStageState).toMap,
        ignoredNotInIndex = genToSet(status.getIgnoredNotInIndex),
        missing = genToSet(status.getMissing),
        modified = genToSet(status.getModified),
        removed = genToSet(status.getRemoved),
        uncommittedChanges = genToSet(status.getUncommittedChanges),
        untracked = genToSet(status.getUntracked)//,
        //untrackedFolders = genToSet(status.getUntrackedFolders)
      )
    }

  }

  def genTextField(destory: File, rewriteAction: () => Unit): List[HBox] = {
    val git = Git.open(destory)
    if (git.getRepository.findRef(Constants.HEAD) eq null) {
      throw new Exception("找不到仓库")
    }
    val paths = git.status().call()
    val statusModel = GitStatus.from(paths)

    val added: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "added"
          style = s"-fx-text-fill: green"
        }
      }
      val fillList = statusModel.added.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "重置"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("重置")
                  println(s)
                  git.reset().addPath(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "added: " + s
              style = s"-fx-text-fill: green"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }

    val removed: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "removed"
          style = s"-fx-text-fill: green"
        }
      }
      val fillList = statusModel.removed.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "重置"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("重置")
                  println(s)
                  git.reset().addPath(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "removed: " + s
              style = s"-fx-text-fill: green"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }

    val changed: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "changed"
          style = s"-fx-text-fill: green"
        }
      }
      val fillList = statusModel.changed.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "重置"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("重置")
                  println(s)
                  git.reset().addPath(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "changed: " + s
              style = s"-fx-text-fill: green"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }

    val untracked: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "untracked"
          style = s"-fx-text-fill: red"
        }
      }
      val fillList = statusModel.untracked.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "添加"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("添加")
                  println(s)
                  git.add().addFilepattern(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "untracked: " + s
              style = s"-fx-text-fill: red"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }
    /*val untrackedFolders: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "untrackedFolders"
          style = s"-fx-text-fill: red"
        }
      }
      val fillList = statusModel.untrackedFolders.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "添加"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("添加")
                  println(s)
                  git.add().addFilepattern(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "untracked: " + s
              style = s"-fx-text-fill: red"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }*/
    val modified: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "modified"
          style = s"-fx-text-fill: red"
        }
      }
      val fillList = statusModel.modified.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "添加"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("添加")
                  println(s)
                  git.add().addFilepattern(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "modified: " + s
              style = s"-fx-text-fill: red"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }

    val missing: List[HBox] = {
      val hbox = new HBox {
        children = new Label {
          text = "missing"
          style = s"-fx-text-fill: red"
        }
      }
      val fillList = statusModel.missing.toList.map { s =>
        new HBox {
          children = List(
            new Button {
              text = "添加"
              handleEvent(ActionEvent.Action) {
                event: ActionEvent =>
                  println("添加")
                  println(s)
                  git.rm().addFilepattern(s).call()
                  rewriteAction()
              }
            },
            new Label {
              text = "missing: " + s
              style = s"-fx-text-fill: red"
            }
          )
        }
      }
      if (fillList.isEmpty) {
        Nil
      } else {
        hbox :: fillList
      }
    }

    added ::: removed ::: changed ::: untracked ::: modified ::: missing/*::: untrackedFolders*/
  }

}