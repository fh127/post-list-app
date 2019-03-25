package com.example.post.list.app.presentation.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * this class sets up offset spacing for adapter items
 * @author fabian hoyos
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public ItemDecoration(int itemOffset) {
        spacing = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(spacing, spacing, spacing, spacing);
    }
}