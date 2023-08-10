package com.app.seafoodapp.model

import com.app.seafoodapp.Interface.Parent

/**
 * Simple implementation of the ParentListItem interface,
 * by default all items are not initially expanded.
 *
 * @param <C> Type of the Child Items held by the Parent.
</C> */
class SimpleParent<C> protected constructor(childItemList: List<C>) :
    Parent<C?> {
    private var mChildList: List<C>
    override fun isInitiallyExpanded(): Boolean {
        return false
    }

    override fun getChildList(): List<C> {
        return mChildList
    }

    fun setChildList(childList: List<C>) {
        mChildList = childList
    }

    init {
        mChildList = childItemList
    }
}