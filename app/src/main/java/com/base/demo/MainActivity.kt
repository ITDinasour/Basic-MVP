package com.base.demo

import com.base.demo.basemvp.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainController.MvpPresenter>(), MainController.MvpView {
    override fun getLayoutId() = R.layout.activity_main
    override fun initPresenter(): MainController.MvpPresenter =
            MainController.MvpPresenterImp(this)

    override fun initData() {
    }

    override fun initView() {
    }
}