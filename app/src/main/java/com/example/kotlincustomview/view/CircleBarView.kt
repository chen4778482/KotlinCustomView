package com.example.kotlincustomview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.kotlincustomview.R
import com.example.kotlincustomview.ulis.ViewUtil


/**
 * 大型进度条
 * */
class CircleBarView : View {
    private val mColorWheelRectangle = RectF()//定义一个矩形,包含矩形的四个单精度浮点坐标

    private var mColorbgPaint: Paint? = null
    private var mColorWheelPaint: Paint? = null//进度条的画笔
    private var mTextPaint: Paint? = null
    private var progress = 0f
    private var anim: BarAnimation? = null
    private var mWidth: Int = 0
    private var circleStrokeWidth = resources.getDimension(R.dimen.x20)

    private var showProgressTxt = true
    //    是否是正向
    private var forward: Boolean = false
    //    开始绘制的角度
    private var startAngle = 0f
    private var mSweepAnglePer = 0f
    private var stepnumbermax = 100// 默认最大时间

    /**
     * 进度条动画
     */
    inner class BarAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)


            mSweepAnglePer = if (interpolatedTime < 1.0f) {
                interpolatedTime * progress * 360f / stepnumbermax
            } else {
                (progress * 360 / stepnumbermax)
            }
            postInvalidate()
        }
    }

    constructor(context: Context) : super(context) {
        initView(null, 0)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        initPaint()
        anim = BarAnimation()
        if (attrs != null) {
            var ta = context.obtainStyledAttributes(attrs, R.styleable.CircleBarView)
            var contentColor = ta.getColor(R.styleable.CircleBar_CircleBar_color, 0)
            progress = ta.getInt(R.styleable.CircleBar_CircleBar_progress, 0).toFloat()
            showProgressTxt = ta.getBoolean(R.styleable.CircleBar_CircleBar_showProgress_txt, true)
            circleStrokeWidth = ta.getDimension(R.styleable.CircleBar_CircleBar_StrokeWidth, resources.getDimension(R.dimen.x20))
            forward = ta.getBoolean(R.styleable.CircleBar_CircleBar_forward, false)
            startAngle = ta.getInt(R.styleable.CircleBar_CircleBar_start_angle, 0).toFloat()

            val x = 360 / 100f
            mSweepAnglePer = progress * x
        }
    }

    private fun initPaint() {
        mColorWheelPaint = Paint()
        mColorWheelPaint?.style = Paint.Style.STROKE
        val shader = LinearGradient(100f, 100f, 500f, 500f, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP)
        mColorWheelPaint?.shader = shader
        mColorWheelPaint?.strokeCap = Paint.Cap.ROUND
        mColorWheelPaint?.isAntiAlias = true
        mColorWheelPaint?.strokeWidth = 20f

        mColorbgPaint = Paint()
        mColorbgPaint?.style = Paint.Style.STROKE
        mColorbgPaint?.color = resources.getColor(R.color.color_ff556170)
        mColorbgPaint?.isAntiAlias = true
        mColorbgPaint?.strokeWidth = 20f

        mTextPaint = Paint()
        mTextPaint?.style = Paint.Style.STROKE// 空心,只绘制轮廓线
        mTextPaint?.strokeCap = Paint.Cap.ROUND// 圆角画笔
        mTextPaint?.isAntiAlias = true// 去锯齿
        mTextPaint?.textSize = resources.getDimension(R.dimen.x28)
        mTextPaint?.color = resources.getColor(R.color.color_ff2d2e35)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(mColorWheelRectangle, 0f, 365f, false, mColorbgPaint)
        if (forward) {
            canvas?.drawArc(mColorWheelRectangle, startAngle, mSweepAnglePer, false, mColorWheelPaint)
        } else {
            canvas?.drawArc(mColorWheelRectangle, startAngle, -mSweepAnglePer, false, mColorWheelPaint)
        }
        //是否显示字
        if (showProgressTxt) {
            var width = width
            var height = height
            var x = (mSweepAnglePer / 360 * 100).toInt()
            var value = x.toString() + "%"
            var leftTxtHeight = Math.abs(ViewUtil.getFontHeight(mTextPaint)) / 2
            var textWidth = Math.abs(mTextPaint!!.measureText(value)) / 2
            canvas?.drawText(value, width / 2 - textWidth, height / 2 + leftTxtHeight, mTextPaint)
        }



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

    /**
     * 更新步数和设置一圈动画时间
     */
    fun update(progress: Int, time: Int) {
        this.progress = progress.toFloat()
        anim?.duration = time.toLong()
        //setAnimationTime(time);
        this.startAnimation(anim)
    }

}