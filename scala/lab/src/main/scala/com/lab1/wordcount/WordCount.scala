package com.lab1.wordcount

import scala.io.Source

object WordCount {
    def main(args:Array[String]):Unit= {
      val filePath = "F:\\Java\\scala\\lab\\data\\test.txt"
      val lineList = Source.fromFile(filePath).getLines().toList
      val wordList = lineList.flatMap(_.split(" "))
      val wordIndex=wordList.map(_->1)
      val groupMap=wordIndex.groupBy(_._1)
      //println(groupMap)
      val result=groupMap.map(keyVal=>keyVal._1->keyVal._2.map(_._2).sum)
      println(result)
    }
}
