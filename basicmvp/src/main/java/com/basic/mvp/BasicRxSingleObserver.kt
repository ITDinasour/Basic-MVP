package com.basic.mvp

import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-12:14
 *    @desc   :
 *    @version: 1.0
 */
open class BasicRxSingleObserver<T>(private var rxManager: RxManager?) : SingleObserver<T> {
    protected var disposable: Disposable?=null

    override fun onSubscribe(d: Disposable) {
        rxManager?.run {
            disposable = d
            add(disposable)
        }
    }


    override fun onError(e: Throwable) {
        rxManager?.remove(disposable)
    }

    override fun onSuccess(data: T) {
        rxManager?.remove(disposable)
    }
}