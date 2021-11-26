package com.base.demo

import com.base.demo.basemvp.BaseMvpController
import com.base.demo.basemvp.BaseMvpModel

/**
 *    @author : Jeffrey
 *    @date   : 2021/11/26-16:41
 *    @desc   :
 *    @version: 1.0
 */
interface MainController : BaseMvpController {

    class MvpPresenterImp(mView: MvpView) :
        BaseMvpController.MvpPresenterImp<MvpView, BaseMvpModel>(mView) ,MvpPresenter{
        override fun getModel(): BaseMvpModel {
            return BaseMvpModel(mContext)
        }
    }

    interface MvpPresenter : BaseMvpController.MvpPresenter {
    }

    interface MvpView : BaseMvpController.MvpView
}