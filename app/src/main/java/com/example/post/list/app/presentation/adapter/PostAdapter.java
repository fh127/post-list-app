package com.example.post.list.app.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.post.list.app.R;
import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.utils.PostUtils;
import com.example.post.list.app.utils.ViewUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * This Adapter class is used to handle Post List
 * @author fabian hoyos
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;
    private Post recentlyDeletedItem;
    private int recentlyDeletedItemPosition;
    private final OnAdapterClickHandler clickHandler;

    public PostAdapter(OnAdapterClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textView.setText(post.getTitle());
        holder.imageButton.setImageResource(ViewUtils.getResourceState(post.getState()));
    }

    @Override
    public int getItemCount() {
        if (postList == null)
            return 0;
        return postList.size();
    }

    public Post getPost(int adapterPosition) {
        return postList.get(adapterPosition);
    }

    public Post getPostToRemove(int adapterPosition) {
        recentlyDeletedItemPosition = adapterPosition;
        return postList.get(adapterPosition);
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    public void deleteSelectedItem() {
        recentlyDeletedItem = postList.get(recentlyDeletedItemPosition);
        postList.remove(recentlyDeletedItemPosition);
        notifyItemRemoved(recentlyDeletedItemPosition);
        clickHandler.onRemoveItem(recentlyDeletedItem.getTitle());
    }

    public void undoDeleteItem() {
        postList.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public void updateItem(Post post) {
        Post selectedPost = PostUtils.findPostById(postList, post.getId());
        if (selectedPost != null) {
            int updateIndex = postList.indexOf(selectedPost);
            postList.set(updateIndex, post);
            notifyItemChanged(updateIndex);
        }
    }

    /**
     * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton imageButton;
        public final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageButton = itemView.findViewById(R.id.image_btn);
            itemView.setOnClickListener(view -> clickHandler.onClickItem(getPost(getAdapterPosition())));
        }
    }

    /**
     * The interface that receives Item events.
     */
    public interface OnAdapterClickHandler {
        void onClickItem(Post post);

        void onRemoveItem(String title);
    }
}