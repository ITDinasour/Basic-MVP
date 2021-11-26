package com.base.demo.basemvp

import android.os.Bundle
import com.basic.withbutterknife.BasicActivity

/**
 *    @author : Jeffrey
 *    @date   : 2021/11/26-16:26
 *    @desc   :
 *    @version: 1.0
 */
abstract class BaseMvpActivity<P : BaseMvpController.MvpPresenter> : BasicActivity(),
    BaseMvpController.MvpView {
    protected lateinit var mPresenter: P
    protected abstract fun initPresenter(): P
    fun getPresenter(): P = mPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        super.onCreate(savedInstanceState)
    }
}