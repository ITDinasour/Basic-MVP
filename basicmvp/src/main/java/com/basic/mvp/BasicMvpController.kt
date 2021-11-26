package com.basic.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-15:52
 *    @desc   :
 *    @version: 1.0
 */
interface BasicMvpController {
    abstract class MvpPresenterImp<V : MvpView, M : BasicMvpModel>(protected val mView: V) :
        MvpPresenter, LifecycleObserver {
        protected var mModel: M
        protected var isDestroy = false
        protected val mContext by lazy { mView }

        init {
            if (mView is LifecycleOwner) {
                mView.lifecycle.addObserver(this@MvpPresenterImp)
            }
            mModel = getModel()
        }

        abstract fun getModel(): M

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        override fun onDestroy() {
            isDestroy = true
            mModel.onDestroy()
        }

    }

    interface MvpPresenter {
        fun onDestroy()
    }

    interface MvpView
}