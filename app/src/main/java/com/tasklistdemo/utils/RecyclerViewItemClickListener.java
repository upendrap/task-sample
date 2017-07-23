package com.tasklistdemo.utils;

/**
 * Created by upenp on 7/23/2017.
 * basic item click handling for recyclerview
 */

public interface RecyclerViewItemClickListener<T> {
    /**
     * method will be invoked when item is clicked in a recyclerview and listener is set properly
     *
     * @param data     the data used to bind view
     * @param position the adapter position of view
     */
    void onItemClick(T data, int position);
}
