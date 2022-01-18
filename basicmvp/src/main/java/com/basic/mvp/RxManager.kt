package com.basic.mvp

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-12:15
 *    @desc   :
 *    @version: 1.0
 */
class RxManager {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun add(d: Disposable?) {
        d?.apply {compositeDisposable.add(this)}
    }

    fun remove(d: Disposable?) {
        d?.apply {compositeDisposable.remove(this)}
    }

    fun clear() {
        compositeDisposable.dispose()
    }
}