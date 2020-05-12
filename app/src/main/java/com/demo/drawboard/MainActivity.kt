package com.demo.drawboard

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : Activity() {
    var colors = arrayOf(-0x123456, -0xff0012, -0xfff123)
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bt_remove.setOnClickListener {
            if (boardview.curState == 1) {
                boardview.curState = 0
            } else {
                boardview.curState = 1
            }
        }

        bt_color.setBackgroundColor(colors[i])
        bt_color.setOnClickListener {
            bt_color.setBackgroundColor(colors[i])
            boardview.curColor = colors[i]
            i = if (++i > 2) 0 else i
        }
    }
}