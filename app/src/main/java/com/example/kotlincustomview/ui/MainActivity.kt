package com.example.kotlincustomview.ui

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlincustomview.R

import com.example.kotlincustomview.act_base.BaseActivity
import com.example.kotlincustomview.bean.MainBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    var mMainAdapter: MainAdapter? = null
    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mMainAdapter = MainAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mMainAdapter
        mMainAdapter?.setNewData(getListMainBean())
        mMainAdapter?.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    var intent = Intent(this@MainActivity, RippleViewActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    var intent = Intent(this@MainActivity, CircleBarActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    var intent = Intent(this@MainActivity, LineActivity::class.java)
                    startActivity(intent)
                }
                3 -> {

                }
                4 -> {

                }
                5 -> {

                }
            }
        }
    }

    class MainAdapter : BaseQuickAdapter<MainBean, BaseViewHolder>(R.layout.mian_item) {
        override fun convert(helper: BaseViewHolder?, item: MainBean?) {
            helper?.setText(R.id.mian_tv, item?.name)
        }
    }

    fun getListMainBean(): MutableList<MainBean> {
        var list = ArrayList<MainBean>()
        list?.add(MainBean("涟漪"))
        list?.add(MainBean("进度圆"))
        list?.add(MainBean("援助"))
        return list
    }
}
