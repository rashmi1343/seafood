package com.app.seafoodapp

import android.content.Context
import android.widget.SpinnerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.database.DataSetObserver
import android.view.View
import android.widget.ListAdapter

class BlockSpinnerAdapter(
    protected var adapter: SpinnerAdapter,
    protected var nothingSelectedLayout: Int,
    protected var nothingSelectedDropdownLayout: Int,
    protected var context: Context?
) : SpinnerAdapter, ListAdapter {
    protected var layoutInflater: LayoutInflater

    /**
     * Use this constructor to have NO 'Select One...' item, instead use
     * the standard prompt or nothing at all.
     * @param spinnerAdapter wrapped Adapter.
     * @param nothingSelectedLayout layout for nothing selected, perhaps
     * you want text grayed out like a prompt...
     * @param context
     */
    constructor(
        spinnerAdapter: SpinnerAdapter,
        nothingSelectedLayout: Int, context: Context?
    ) : this(spinnerAdapter, nothingSelectedLayout, -1, context) {
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        // This provides the View for the Selected Item in the Spinner, not
        // the dropdown (unless dropdownView is not set).
        return if (position == 0) {
            getNothingSelectedView(parent)
        } else adapter.getView(
            position - EXTRA,
            null,
            parent
        )
        // Could re-use
        // the convertView if possible.
    }

    protected fun getNothingSelectedView(parent: ViewGroup?): View {
        return layoutInflater.inflate(nothingSelectedLayout, parent, false)
    }

    protected fun getNothingSelectedDropdownView(parent: ViewGroup?): View {
        return layoutInflater.inflate(nothingSelectedDropdownLayout, parent, false)
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View? {
        return null
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }

    override fun getCount(): Int {
        val count = adapter.count
        return if (count == 0) 0 else count + EXTRA
    }

    override fun getItem(position: Int): Any {
        return (if (position == 0) null else adapter.getItem(position - EXTRA))!!
    }

    override fun getItemId(position: Int): Long {
        return if (position >= EXTRA) adapter.getItemId(position - EXTRA) else (position - EXTRA).toLong()
    }

    override fun hasStableIds(): Boolean {
        return adapter.hasStableIds()
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return adapter.isEmpty
    }

    companion object {
        protected const val EXTRA = 1
    }

    /**
     * Use this constructor to Define your 'Select One...' layout as the first
     * row in the returned choices.
     * If you do this, you probably don't want a prompt on your spinner or it'll
     * have two 'Select' rows.
     * @param spinnerAdapter wrapped Adapter. Should probably return false for isEnabled(0)
     * @param nothingSelectedLayout layout for nothing selected, perhaps you want
     * text grayed out like a prompt...
     * @param nothingSelectedDropdownLayout layout for your 'Select an Item...' in
     * the dropdown.
     * @param context
     */
    init {
        layoutInflater = LayoutInflater.from(context)
    }
}