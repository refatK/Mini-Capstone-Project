package com.fsck.k9.fragment;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public interface MessageListAdapterType extends ListAdapter {
    @Override
    boolean areAllItemsEnabled();

    @Override
    boolean isEnabled(int position);

    @Override
    void registerDataSetObserver(DataSetObserver observer);

    @Override
    void unregisterDataSetObserver(DataSetObserver observer);

    @Override
    int getCount();

    @Override
    Object getItem(int position);

    @Override
    long getItemId(int position);

    @Override
    boolean hasStableIds();

    @Override
    View getView(int position, View convertView, ViewGroup parent);

    @Override
    int getItemViewType(int position);

    @Override
    int getViewTypeCount();

    @Override
    boolean isEmpty();

    void notifyDataSetChanged();

    Cursor swapCursor(Cursor c);
}
