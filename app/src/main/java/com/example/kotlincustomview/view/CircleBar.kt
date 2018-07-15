package com.example.kotlincustomview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.example.kotlincustomview.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by 42224 on 2018/5/16.
 */
class CircleBar : View {

    var mColorWheelRectF = RectF()
    var mColorWheelPaint = Paint()//进度条的画笔
    var mColorWheelPaint_bg = Paint()//背景的画笔
    var mTextPaint = Paint()//字体的画笔

    constructor(context: Context) : super(context) {
        initView(null)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        init()
        if (attrs != null) {
            //从xml的属性中获取到字体颜色与string
            var ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBar)
            //进度条颜色
            var mCircleBar_color: Int = ta.getColor(R.styleable.CircleBar_CircleBar_color, 0)
            //进度值
            var mCircleBar_progress = ta.getInt(R.styleable.CircleBar_CircleBar_progress, 0)
            //是否显示精度百分百字体
            var mCircleBar_showProgress_txt = ta.getBoolean(R.styleable.CircleBar_CircleBar_showProgress_txt, true)
            //背景线条颜色
            var mCircleBar_bg_StrokeColor = ta.getColor(R.styleable.CircleBar_CircleBar_bg_StrokeColor, 0)
            //背景线条粗细
            var mCircleBar_bg_StrokeWidth = ta.getDimension(R.styleable.CircleBar_CircleBar_bg_StrokeWidth, resources.getDimension(R.dimen.x3))
            //线条粗细
            var mCircleBar_StrokeWidth = ta.getDimension(R.styleable.CircleBar_CircleBar_StrokeWidth, resources.getDimension(R.dimen.x8))
            //是否是正向
            var mCircleBar_forward = ta.getBoolean(R.styleable.CircleBar_CircleBar_forward, true)
            //开始角度
            var mCircleBar_start_angle = ta.getInt(R.styleable.CircleBar_CircleBar_start_angle, 0)
            if (mCircleBar_bg_StrokeColor != 0) {
                mColorWheelPaint_bg.color = mCircleBar_bg_StrokeColor
            }
            if (mCircleBar_color != 0) {
                mColorWheelPaint.color = mCircleBar_color
            }
        }

    }

    private fun init() {
        mColorWheelPaint_bg.style = Paint.Style.STROKE //画线
        mColorWheelPaint_bg.isAntiAlias = true
        mColorWheelPaint_bg.strokeCap = Paint.Cap.ROUND
        mColorWheelPaint_bg.color = ContextCompat.getColor(context, R.color.color_ffd0423d)

        mColorWheelPaint.style = Paint.Style.STROKE //画线
        mColorWheelPaint.isAntiAlias = true
        mColorWheelPaint.strokeCap = Paint.Cap.ROUND
        mColorWheelPaint.color = ContextCompat.getColor(context, R.color.color_ffe1e1e1)

        mTextPaint.style = Paint.Style.STROKE
        mTextPaint.isAntiAlias = true
        mTextPaint.strokeCap = Paint.Cap.ROUND
        mTextPaint.textSize = resources.getDimension(R.dimen.x28)
        mTextPaint.color = ContextCompat.getColor(context, R.color.color_ff2d2e35)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mColorWheelRectF.set(200f, 200f, 200f, 200f)
        canvas!!.drawArc(mColorWheelRectF, 200f, 200f, true, mColorWheelPaint)
        canvas!!.drawArc(mColorWheelRectF, 150f, 150f, true, mColorWheelPaint_bg)
        canvas!!.drawText("80%", 200f, 200f, mTextPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)// 获取View最短边的长度
        setMeasuredDimension(min, min)// 强制改View为以最短边为长度的正方形
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}