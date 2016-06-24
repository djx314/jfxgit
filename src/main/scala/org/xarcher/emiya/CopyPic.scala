package org.xarcher.xPhoto

import java.awt.{Color, Font, Point, Toolkit}
import java.awt.datatransfer.{DataFlavor, Transferable, UnsupportedFlavorException}
import java.io.{File, FileInputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.imageio.ImageIO

import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.filters.{Canvas, Caption}
import net.coobird.thumbnailator.geometry.{Position, Positions}
import org.apache.commons.io.FileUtils

import scala.annotation.tailrec
import scala.util.Try

object CopyPic {

  def pic(inputStream: InputStream)(content: String): Unit = {

  }

  def convert(file: File)(needToReWidth: Boolean)(width: Int)(needToWater: Boolean): Unit = {
  }

}