package com.example.kotlincustomview.view


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.kotlincustomview.R


/**
 * Created by 42224 on 2018/5/16.
 */
class RippleViewTo : View {
    /**
     * 三个圆的画笔
     */
    private var mLinePaint1: Paint? = null
    private var mLinePaint2: Paint? = null
    private var mLinePaint3: Paint? = null
    /**
     * 最内圆的坐标
     */
    private val mRectF1 = RectF()
    /**
     * 第二个圆
     */
    private val mRectF2 = RectF()
    /**
     * 最外圆
     */
    private val mRectF3 = RectF()
    /**
     * 动画
     */
    private var rippleAnimation: RippleAnimation? = null
    /**
     * 外面两个圆绘制进度
     */
    private var progress = 0f
    /**
     * view的宽度
     */
    private var mWidth = 0

    inner class RippleAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            progress = interpolatedTime
            postInvalidate()
        }
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    /**
     * 初始化画笔和动画
     */
    private fun initView() {
        initPaint()
        rippleAnimation = RippleAnimation()
        rippleAnimation!!.repeatCount = ValueAnimator.INFINITE//动画模式无限循环
        rippleAnimation!!.repeatMode = ValueAnimator.RESTART
    }

    /**
     * 开始动画
     */
    fun startAnimation() {
        rippleAnimation!!.duration = 1000
        this.startAnimation(rippleAnimation)
    }

    /**
     * 初始化三个圆对应的画笔颜色、线条宽度
     */
    private fun initPaint() {
        mLinePaint1 = Paint()
        mLinePaint1!!.style = Paint.Style.STROKE
        mLinePaint1!!.strokeCap = Paint.Cap.ROUND
        mLinePaint1!!.isAntiAlias = true
        mLinePaint1!!.color = resources.getColor(R.color.color_ffffffff)
        mLinePaint1!!.strokeWidth = resources.getDimension(R.dimen.x5)

        mLinePaint2 = Paint()
        mLinePaint2!!.style = Paint.Style.STROKE
        mLinePaint2!!.strokeCap = Paint.Cap.ROUND
        mLinePaint2!!.isAntiAlias = true
        mLinePaint2!!.color = resources.getColor(R.color.color_99ffffff)
        mLinePaint2!!.strokeWidth = resources.getDimension(R.dimen.x2)

        mLinePaint3 = Paint()
        mLinePaint3!!.style = Paint.Style.STROKE
        mLinePaint3!!.strokeCap = Paint.Cap.ROUND
        mLinePaint3!!.isAntiAlias = true
        mLinePaint3!!.color = resources.getColor(R.color.color_3dffffff)
        mLinePaint3!!.strokeWidth = resources.getDimension(R.dimen.x1)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val middle = mWidth / 2//获取View的中心点
        val radius175 = resources.getDimension(R.dimen.x175)//第一个圆的半径175px
        val radius80 = resources.getDimension(R.dimen.x80)//第二个圆距离第一个圆的半径80px
        val radius160 = resources.getDimension(R.dimen.x160)//第三个圆距离第一个圆的半径80px

        val leftAndTop = middle - radius175//第一个圆左 上 边的坐标
        val rightAndBottom = middle + radius175//第一个圆右 下 边的坐标

        val leftAndTop_1 = leftAndTop - radius80 * progress//第二个圆左 上 边的坐标 根据progress进度动态绘制
        val rightAndBottom_1 = rightAndBottom + radius80 * progress//第二个圆右 下 边的坐标

        val leftAndTop_2 = leftAndTop - radius160 * progress//第三个圆左 上 边的坐标 根据progress进度动态绘制
        val rightAndBottom_2 = rightAndBottom + radius160 * progress//第三个圆右 下 边的坐标

        mRectF1.set(leftAndTop, leftAndTop, rightAndBottom, rightAndBottom)
        mRectF2.set(leftAndTop_1, leftAndTop_1, rightAndBottom_1, rightAndBottom_1)
        mRectF3.set(leftAndTop_2, leftAndTop_2, rightAndBottom_2, rightAndBottom_2)

        mLinePaint2!!.alpha = (255 - progress * 255).toInt()
        mLinePaint3!!.alpha = (255 - progress * 255).toInt()
        canvas.drawArc(mRectF1, 0f, 359f, false, mLinePaint1!!)
        canvas.drawArc(mRectF2, 0f, 359f, false, mLinePaint2!!)
        canvas.drawArc(mRectF3, 0f, 359f, false, mLinePaint3!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        mWidth = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(mWidth, height)// 获取View最短边的长度
        setMeasuredDimension(min, min)// 强制改View为以最短边为长度的正方形
    }

}