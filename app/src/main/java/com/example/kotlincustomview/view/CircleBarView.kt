package com.example.kotlincustomview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.graphics.Color.parseColor
import com.example.kotlincustomview.R


/**
 * 大型进度条
 * */
class CircleBarView : View {
    private val mColorWheelRectangle = RectF()//定义一个矩形,包含矩形的四个单精度浮点坐标
    private var mColorbgPaint:Paint?=null
    private var mColorWheelPaint: Paint? = null//进度条的画笔
    private var mTextPaint: Paint? = null
    private var progress = 0f
    private var anim: BarAnimation? = null
    private var mWidth: Int = 0
    private var circleStrokeWidth= resources.getDimension(R.dimen.x20)


    /**
     * 进度条动画
     */
    inner class BarAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            progress = interpolatedTime
            postInvalidate()
        }
    }
    constructor(context: Context) : super(context) {
        initView()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        initPaint()
    }

    private fun initPaint() {
        mColorWheelPaint= Paint()
        mColorWheelPaint?.style=Paint.Style.STROKE
        val shader = LinearGradient(100f, 100f, 500f, 500f, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP)
        mColorWheelPaint?.shader = shader
        mColorWheelPaint?.strokeCap=Paint.Cap.ROUND
        mColorWheelPaint?.isAntiAlias=true
        mColorWheelPaint?.strokeWidth=20f

        mColorbgPaint= Paint()
        mColorbgPaint?.style=Paint.Style.STROKE
        mColorbgPaint?.color=resources.getColor(R.color.color_ff556170)
        mColorbgPaint?.isAntiAlias=true
        mColorbgPaint?.strokeWidth=20f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(mColorWheelRectangle, 0f, 365f, false, mColorbgPaint)

        canvas?.drawArc(mColorWheelRectangle, -90f, 100f, false, mColorWheelPaint)



        super.onDraw(canvas)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        mWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        var min = Math.min(mWidth, height)// 获取View最短边的长度
        setMeasuredDimension(min, min)// 强制改View为以最短边为长度的正方形

        val pressExtraStrokeWidth = Textscale(2f, min.toFloat())// 圆弧离矩形的距离
        mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
                circleStrokeWidth + pressExtraStrokeWidth, min.toFloat()
                - circleStrokeWidth - pressExtraStrokeWidth, min.toFloat()
                - circleStrokeWidth - pressExtraStrokeWidth)// 设置矩形
    }

    /**
     * 根据控件的大小改变绝对位置的比例
     */
    fun Textscale(n: Float, m: Float): Float {
        return n / 500 * m
    }
}