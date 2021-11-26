package com.base.demo.basemvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.withbutterknife.BasicFragment

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-14:22
 *    @desc   :
 *    @version: 1.0
 */
abstract class BaseMvpFragment<P : BaseMvpController.MvpPresenter, A : BaseMvpActivity<*>> :
    BasicFragment<A>(), BaseMvpController.MvpView {

    protected lateinit var mPresenter: P
    protected abstract fun initPresenter(): P

    fun getPresenter(): P {
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = initPresenter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        mPresenter.onDestroy()
        super.onDestroyView()
    }
}