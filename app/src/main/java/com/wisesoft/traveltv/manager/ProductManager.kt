package com.wisesoft.traveltv.manager

import com.wisesoft.traveltv.constants.Constans
import com.wisesoft.traveltv.ui.change.HomeTab

/**
 * Created by picher on 2018/4/3.
 * Describe：
 */

class ProductManager private constructor() {

    fun getPageType(homeTab: HomeTab)= when (homeTab) {
            HomeTab.TAB_SEARCH -> Constans.TYPE_SEARCH
            HomeTab.TAB_RECOMMEND -> Constans.TYPE_RECOMMEND
            HomeTab.TAB_PLAY -> Constans.TYPE_PLAY
            HomeTab.TAB_EAT -> Constans.TYPE_EAT
            HomeTab.TAB_STAY -> Constans.TYPE_STAY
            HomeTab.TAB_PAY -> Constans.TYPE_PAY
            HomeTab.TAB_FUN -> Constans.TYPE_FUN
    }

    companion object {

        private var mInstance: ProductManager? = null

        val instance: ProductManager
            get() {
                if (mInstance == null) {
                    synchronized(ProductManager::class.java) {
                        mInstance = ProductManager()
                    }
                }
                return this.mInstance!!
            }
    }
}
