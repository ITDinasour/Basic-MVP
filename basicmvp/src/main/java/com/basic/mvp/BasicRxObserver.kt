package com.basic.mvp

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-12:14
 *    @desc   :
 *    @version: 1.0
 */
abstract class BasicRxObserver<T>(private var rxManager: RxManager?) : Observer<T> {
    protected var disposable: Disposable?=null

    override fun onSubscribe(d: Disposable) {
        rxManager?.run {
            disposable = d
            add(disposable)
        }
    }

    override fun onComplete() {
        rxManager?.remove(disposable)
    }

    override fun onError(e: Throwable) {
        rxManager?.remove(disposable)
    }

    class NullThrowable(message: String?) : Throwable(message) {
    }

}