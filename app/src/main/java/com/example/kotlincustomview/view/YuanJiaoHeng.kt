package com.example.kotlincustomview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.kotlincustomview.R
import com.example.kotlincustomview.ulis.ViewUtil


/**
 * Created by 42224 on 2018/6/27.
 */

class YuanJiaoHeng : View {

    /**
     * 动画
     */
    private var rippleAnimation: RippleAnimation? = null

    /**
     *  绘制进度
     */
    private var progress = 0f

    /**
     * 画笔
     */
    private var mLinePaint1: Paint? = null
    private var mLinePaint2: Paint? = null

    /**
     * 高度
     * */
    var height: Int? = null
    /**
     * 宽度
     * */
    var width: Int? = null

    var number: String? = null

    /**
     * 比例
     */
    private var wid = 1f

    constructor(context: Context) : super(context) {
        initview()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initview()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initview()
    }

    fun initview() {
        mLinePaint1 = Paint()
        mLinePaint1?.style = Paint.Style.FILL
        mLinePaint1?.strokeCap = Paint.Cap.ROUND// 圆角画笔
        mLinePaint1?.color = resources.getColor(R.color.error_color_material)
        mLinePaint1?.isAntiAlias = true// 去锯齿
        mLinePaint1?.strokeWidth = 5f

        mLinePaint2 = Paint()
        mLinePaint2?.color = resources.getColor(R.color.material_grey_100)
        mLinePaint2?.isAntiAlias = true// 去锯齿
        mLinePaint2?.textSize = 36f
        rippleAnimation = RippleAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var heigh = (height!!.toFloat())
        var widt = (width!!.toFloat()) * progress

        var x = (widt * wid) - 10f

        if (x > 30) {
            // 画圆角矩形(RectF)
            var rectrf = RectF(10f, 10f, x, heigh - 10f)
            canvas.drawRoundRect(rectrf, 30f, 30f, mLinePaint1)

            val leftTxtHeight = Math.abs(ViewUtil.getFontHeight(mLinePaint2)) / 2
            val textWidth = Math.abs(mLinePaint2!!.measureText("12")) / 2
            val wi = (x - textWidth - 50f) * progress

            if (number != null) {
                canvas.drawText(number, wi, heigh / 2 + leftTxtHeight, mLinePaint2)
            }
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        height = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    inner class RippleAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            progress = interpolatedTime
            postInvalidate()
        }
    }

    /**
     * 设置进度条颜色
     */
    fun setColor(red: Int) {
        mLinePaint1?.color = resources.getColor(red)
    }

    /**
     * 设置文字
     */
    fun setText(number: String) {
        this.number = number
    }

    /**
     * 设置长度
     */
    fun setChangDu(number: Float, t: Float) {
        if ((number / t) > 0.12f) {
            wid = (number / t)
        } else {
            wid = 0.12f
        }


    }

    /**
     * 设置动画时间
     */
    fun update(time: Int) {
        rippleAnimation?.duration = time.toLong()
        this.startAnimation(rippleAnimation)
    }
}
