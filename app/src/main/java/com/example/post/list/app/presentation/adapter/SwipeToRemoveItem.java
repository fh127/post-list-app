package com.example.post.list.app.presentation.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is used to handle swipe left/right to remove adapter items
 * it uses {@link ItemTouchHelper} class
 * @author fabian hoyos
 */
public class SwipeToRemoveItem extends ItemTouchHelper.SimpleCallback {

    private SwipeEvents listener;
    private Drawable icon;
    private final ColorDrawable background;

    public SwipeToRemoveItem(Context context, SwipeEvents listener) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.listener = listener;
        icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete);
        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        listener.onSwipeItem(position);
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float distanceX, float distanceY,
                            int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(canvas, recyclerView, viewHolder, distanceX, distanceY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 30; //so background is behind the rounded corners of itemView
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (distanceX > 0) { // Swiping to the right
            setupSwipeRight(itemView, iconTop, iconBottom, iconMargin, distanceX, backgroundCornerOffset);
        } else if (distanceX < 0) { // Swiping to the left
            setupSwipeLeft(itemView, iconTop, iconBottom, iconMargin, distanceX, backgroundCornerOffset);
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(canvas);
        icon.draw(canvas);
    }

    private void setupSwipeRight(View itemView, int iconTop, int iconBottom, int iconMargin, float distanceX, int backgroundCornerOffset) {
        int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
        int iconRight = itemView.getLeft() + iconMargin;
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) distanceX) + backgroundCornerOffset, itemView.getBottom());
    }

    private void setupSwipeLeft(View itemView, int iconTop, int iconBottom, int iconMargin, float distanceX, int backgroundCornerOffset) {
        int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
        int iconRight = itemView.getRight() - iconMargin;
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        background.setBounds(itemView.getRight() + ((int) distanceX) - backgroundCornerOffset,
                itemView.getTop(), itemView.getRight(), itemView.getBottom());
    }

    public interface SwipeEvents {

        void onSwipeItem(int position);
    }

}