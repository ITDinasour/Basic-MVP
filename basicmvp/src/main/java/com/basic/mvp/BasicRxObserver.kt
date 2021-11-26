package com.basic.mvp

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-12:14
 *    @desc   :
 *    @version: 1.0
 */
abstract class BasicRxObserver<T> : Observer<T> {
    private var rxManager: RxManager? = null;
    private lateinit var disposable: Disposable;

    constructor() {}
    constructor(rxManager: RxManager) {
        this.rxManager = rxManager;
    }

    override fun onSubscribe(d: Disposable) {
        rxManager?.run {
            disposable = d
            this.add(disposable)
        }
    }

    override fun onComplete() {
        rxManager?.run {
            remove(disposable)
        }
    }

    override fun onError(e: Throwable) {
        rxManager?.run {
            remove(disposable)
        }
    }

    class NullThrowable(message: String?) : Throwable(message) {
    }

}