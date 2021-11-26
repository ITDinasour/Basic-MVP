package com.base.demo.basemvp

import androidx.lifecycle.LifecycleOwner
import com.basic.mvp.BasicMvpController
import com.basic.withoutbinding.BasicView

/**
 *    @author : Jeffrey
 *    @date   : 2021/11/26-16:41
 *    @desc   :
 *    @version: 1.0
 */
interface BaseMvpController : BasicMvpController {

    abstract class MvpPresenterImp<V : MvpView, M : BaseMvpModel>(mView: V) :
        BasicMvpController.MvpPresenterImp<V, M>(mView),MvpPresenter {
        protected val mContext by lazy { mView.getContext() }

        init {
            if (mView !is LifecycleOwner) {
                mView.getContext().apply {
                    if (this is LifecycleOwner) {
                        lifecycle.addObserver(this@MvpPresenterImp)
                    }
                }
            }
        }
    }

    interface MvpPresenter : BasicMvpController.MvpPresenter {
    }

    interface MvpView : BasicMvpController.MvpView, BasicView
}