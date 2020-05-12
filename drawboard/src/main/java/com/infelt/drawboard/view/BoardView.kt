package com.infelt.drawboard.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.infelt.drawboard.view.bean.PathBean
import com.infelt.drawboard.view.model.BoardData
import com.infelt.drawboard.view.model.IBoard


class BoardView(context: Context?, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs), IBoard {

    private var mPath: Path? = null
    private var mPaint: Paint? = null
    var curEvent: MotionEvent? = null

    var curColor = Color.RED
    var curStrokeWidth = 5f
    var curState: Int = 0

    init {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = curStrokeWidth
        mPaint?.strokeCap = Paint.Cap.ROUND
        mPaint?.color = curColor
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制之前绘制的线条
        mPaint?.let {
            var pathList = BoardData.instance.getPathList()
            for (pathBean in pathList) {
                pathBean.path?.let { it1 ->
                    it.strokeWidth = pathBean.strokeWidth
                    it.color = pathBean.color
                    canvas?.drawPath(it1, it)
                }
            }
        }

        //绘制当前正在绘制的线条
        mPath?.let {
            mPaint?.let { it1 ->
                it1.strokeWidth = curStrokeWidth
                it1.color = curColor
                canvas?.drawPath(it, it1)
            }
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        curEvent = event;
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (curState == Constants.TOOLS_PEN) {
                    mPath = Path()
                    mPath?.moveTo(event.x, event.y)
                }
            }
            MotionEvent.ACTION_UP -> {
                event?.let {
                    if (curState == Constants.TOOLS_PEN) {
                        mPath?.let { it1 ->
                            it1?.lineTo(it.x, it.y)
                            var pathBean = PathBean();
                            pathBean.path = it1
                            pathBean.color = curColor
                            pathBean.strokeWidth = curStrokeWidth
                            BoardData.instance.addPath(pathBean)
                        }
                        mPath = null
                    } else {
                        BoardData.instance.deletePath(it.x, it.y)
                    }
                }
            }
            else -> {
                if (curState == Constants.TOOLS_PEN) {
                    event?.let {
                        mPath?.lineTo(it.x, it.y)
                    }
                }
            }
        }
        postInvalidate()
        return true
    }

    override fun setTools(tools: Int) {
        curState = tools
    }

    override fun setPaintColor(color: Int) {
        mPaint?.let {
            it.color = color
        }
    }

    override fun setStrokeWidth(with: Float) {
        mPaint?.let {
            it.strokeWidth = with
        }
    }


}

