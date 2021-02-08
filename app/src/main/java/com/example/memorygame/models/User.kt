package com.example.memorygame.models

import java.io.Serializable

class User : Serializable{

    var id : Int = 0
    var name : String = ""
    var timeFloat : Float = 0f
    var time : String = ""
    var boardType : String  = ""


    constructor(name:String,timeFloat:Float, boardSize: BoardSize,time : String){
        this.name = name
        this.timeFloat = timeFloat
        this.time = time

        var size =""
        if(boardSize == BoardSize.NORMAL){
            size = "Normal"
        }
        else size = "Big"
        this.boardType = size
    }

    constructor(){
    }

}