package com.infelt.drawboard.view.model

interface IBoard {
    fun setTools(tools: Int);
    fun setPaintColor(color: Int);
    fun setStrokeWidth(with: Float);
}