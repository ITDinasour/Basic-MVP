package com.basic.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Cancellable
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate
import io.reactivex.rxjava3.internal.operators.single.SingleCreate
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 *    @author : Jeffrey
 *    @date   : 2021/6/21-14:22
 *    @desc   :
 *    @version: 1.0
 */
open class BasicMvpModel(lifecycleOwner: LifecycleOwner?) : LifecycleObserver {
    protected val rxManager: RxManager = RxManager()

    init {
        lifecycleOwner?.apply { lifecycle.addObserver(this@BasicMvpModel) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        rxManager.clear()
    }

    fun <T> singleActionOnRxIoMainThread(
        actionDo: ((SingleEmitterProxy<T>) -> Unit),
        successAction: ((T) -> Unit)? = null,
        errorAction: ((Throwable) -> Unit)? = null,
        subscribeAction: ((DisposableProxy) -> Unit)? = null
    ) {
        singleActionOnRxIoMainThread(
            actionDo, successAction, errorAction, subscribeAction, rxManager
        )
    }

    fun <T> observeActionOnRxIoMainThread(
        actionDo: ((ObservableEmitterProxy<T>) -> Unit),
        next: ((T) -> Unit)? = null,
        error: ((Throwable) -> Unit)? = null,
        complete: (() -> Unit)? = null,
        subscribe: ((DisposableProxy) -> Unit)? = null
    ) {
        observeActionOnRxIoMainThread(actionDo, next, error, complete, subscribe, rxManager)
    }

    companion object {
        fun <T> singleActionOnRxIoMainThread(
            actionDo: ((SingleEmitterProxy<T>) -> Unit),
            successAction: ((T) -> Unit)? = null,
            errorAction: ((Throwable) -> Unit)? = null,
            subscribeAction: ((DisposableProxy) -> Unit)? = null,
            rxManager: RxManager? = null
        ) {
            SingleCreate.create<T> {
                actionDo(SingleEmitterProxy(it))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BasicRxSingleObserver<T>(rxManager) {

                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        subscribeAction?.invoke(DisposableProxy(d))
                    }

                    override fun onSuccess(data: T) {
                        super.onSuccess(data)
                        successAction?.invoke(data)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        errorAction?.invoke(e)
                    }
                })
        }

        fun <T> observeActionOnRxIoMainThread(
            actionDo: ((ObservableEmitterProxy<T>) -> Unit),
            next: ((T) -> Unit)? = null,
            error: ((Throwable) -> Unit)? = null,
            complete: (() -> Unit)? = null,
            subscribe: ((DisposableProxy) -> Unit)? = null,
            rxManager: RxManager? = null
        ) {
            ObservableCreate.create(ObservableOnSubscribe<T> {
                actionDo(ObservableEmitterProxy(it))
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BasicRxObserver<T>(rxManager) {
                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        subscribe?.invoke(DisposableProxy(d))
                    }

                    override fun onNext(data: T) {
                        if (disposable?.isDisposed != true) {
                            next?.invoke(data)
                        }
                    }

                    override fun onComplete() {
                        super.onComplete()
                        complete?.invoke()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        error?.invoke(e)
                    }
                })
        }
    }

    class SingleEmitterProxy<T>(private val emitter: SingleEmitter<T>) {

        fun onSuccess(t: T) = emitter.onSuccess(t)
        fun onError(t: Throwable) = emitter.onError(t)
        fun setDisposable(d: Disposable?) = emitter.setDisposable(d)
        fun setCancellable(c: Cancellable?) = emitter.setCancellable(c)
        fun isDisposed(): Boolean = emitter.isDisposed
        fun tryOnError(t: Throwable): Boolean = emitter.tryOnError(t)
        inline fun <reified T> onCheckNullAction(any: T?, action: ((T) -> Unit)) {
            any?.apply {
                action(this)
            } ?: apply {
                onError(Throwable(T::class.java.simpleName + " was Null"))
            }
        }
    }

    class ObservableEmitterProxy<T>(private val emitter: ObservableEmitter<T>) {
        fun isDisposed(): Boolean = emitter.isDisposed
        fun tryOnError(t: Throwable): Boolean = emitter.tryOnError(t)
        fun onComplete() = emitter.onComplete()
        fun setCancellable(c: Cancellable?) = emitter.setCancellable(c)
        fun setDisposable(d: Disposable?) = emitter.setDisposable(d)
        fun serialize(): ObservableEmitterProxy<T> =
            ObservableEmitterProxy(emitter.serialize())

        fun onNext(value: T) = emitter.onNext(value)
        fun onError(error: Throwable) = emitter.onError(error)
        inline fun <reified T> onCheckNullAction(any: T?, action: ((T) -> Unit)) {
            any?.apply {
                action(this)
            } ?: apply {
                onError(Throwable(T::class.java.simpleName + " was Null"))
            }
        }
    }

    class DisposableProxy(private val disposable: Disposable) {
        fun dispose() = disposable.dispose()

        /**
         * Returns true if this resource has been disposed.
         * @return true if this resource has been disposed
         */
        fun isDisposed() = disposable.isDisposed()
    }
}