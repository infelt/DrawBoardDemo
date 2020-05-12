package com.infelt.drawboard.view.model

import android.graphics.RectF
import android.graphics.Region
import android.util.Log
import com.infelt.drawboard.view.bean.PathBean
import java.util.concurrent.CopyOnWriteArrayList

class BoardData {


    companion object {
        val instance: BoardData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BoardData()
        }

        //存储的绘制的线条
        private var pathList: CopyOnWriteArrayList<PathBean> = CopyOnWriteArrayList()

        //存储的删除的线条
        private var deletePathList: CopyOnWriteArrayList<PathBean> = CopyOnWriteArrayList()
    }

    public fun getPathList(): List<PathBean> {
        return pathList
    }

    public fun addPath(path: PathBean) {
        pathList.add(path)
        deletePathList.clear()
    }


    public fun clearData() {
        pathList.clear()
        deletePathList.clear()
    }

    public fun deletePath(x: Float, y: Float) {
        Log.d("infelt", "pre deletePath " + pathList.size)
        val bounds = RectF()
        val region = Region()
        var iterator = pathList.iterator()
        var deletePathBean: PathBean? = null
        while (iterator.hasNext()) {
            var tmpPathBean = iterator.next()
            var tmpPath = tmpPathBean.path
            tmpPath?.computeBounds(bounds, true)
            region.setPath(
                tmpPath,
                Region(
                    bounds.left.toInt(),
                    bounds.top.toInt(),
                    bounds.right.toInt(),
                    bounds.bottom.toInt()
                )
            )
            if (region.contains(x.toInt(), y.toInt())) {
                deletePathBean = tmpPathBean
                break
            } else {
            }
        }
        pathList.remove(deletePathBean)
        deletePathList.add(deletePathBean)
    }

    public fun resumePath(): Boolean {
//        if (deletePathList.size > 0) {
//            pathList.add(deletePathList.dropLast(1))
//        }
        return false
    }
}