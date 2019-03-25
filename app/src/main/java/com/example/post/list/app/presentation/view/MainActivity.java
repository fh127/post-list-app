package com.example.post.list.app.presentation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.post.list.app.R;
import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.presentation.adapter.ItemDecoration;
import com.example.post.list.app.presentation.adapter.PostAdapter;
import com.example.post.list.app.presentation.adapter.SwipeToRemoveItem;
import com.example.post.list.app.presentation.presenters.BasePresenter;
import com.example.post.list.app.presentation.presenters.PostContractMainView;
import com.example.post.list.app.utils.Constants;
import com.example.post.list.app.utils.ViewUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;

import static com.example.post.list.app.presentation.presenters.PostContractMainView.Presenter;
import static com.example.post.list.app.utils.Constants.SELECTED_POST_KEY;


/**
 * @author fabian hoyos
 */
public class MainActivity extends AppCompatActivity implements PostContractMainView.View, PostAdapter.OnAdapterClickHandler {

    @Inject
    Presenter mainPresenter;
    private PostAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar loader;
    private Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupRecyclerView();
        setupRemovePostListButton();
        setupLoaderView();
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(Constants.PARCELABLE_STATE_LIST);
        }
        mainPresenter.getPostList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((BasePresenter) mainPresenter).init();
    }

    @Override
    protected void onDestroy() {
        ((BasePresenter) mainPresenter).release();
        super.onDestroy();
    }

    private void setupLoaderView() {
        loader = findViewById(R.id.loader_view);
        loader.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        String defaultTitle = menu.findItem(R.id.filter_label).getTitle().toString();
        Spannable spannable = ViewUtils.getSpan(mainPresenter.getFilterMenuOption(defaultTitle), Color.WHITE, Typeface.BOLD_ITALIC);
        menu.findItem(R.id.filter_label).setTitle(spannable);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                handleMenuItemCheckEvent(item);
                return true;
            case R.id.action_favorite:
                handleMenuItemCheckEvent(item);
                return true;
            case R.id.action_refresh:
                mainPresenter.refreshList();
                showMessage("refresh");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void updateFilterOption(MenuItem item) {
        int optionId = ViewUtils.filterMenuUiOption.get(item.getItemId());
        ActionMenuItemView menuItem = findViewById(R.id.filter_label);
        Spannable spannable = ViewUtils.getSpan(item.getTitle().toString(), Color.WHITE, Typeface.BOLD_ITALIC);
        menuItem.setTitle(spannable);
        mainPresenter.setFilterMenuOption(optionId, item.getTitle().toString());
        mainPresenter.getPostList();
    }

    private void handleMenuItemCheckEvent(MenuItem item) {
        item.setChecked(true);
        updateFilterOption(item);
    }

    // UI settigns
    private void setupRecyclerView() {
        adapter = new PostAdapter(this);
        recyclerView = findViewById(R.id.recycler_view);
        final Context context = recyclerView.getContext();
        final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDecoration(spacing));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToRemoveItem(context, position -> mainPresenter.removePost(adapter.getPostToRemove(position))));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupRemovePostListButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, R.string.remove_question_label, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes_label, v -> mainPresenter.removePosts())
                .show());
    }

    private void showPostList(List<Post> postList) {
        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        adapter.setPostList(postList);
    }

    // presenter actions
    @Override
    public void onPostList(List<Post> postList) {
        showPostList(postList);
        onShowLoader(false);
    }

    @Override
    public void onRefresh() {
        mainPresenter.getPostList();
    }

    @Override
    public void onPostListRemoved() {
        adapter.setPostList(new ArrayList<>());
        showMessage(getString(R.string.removed_post_list));
    }

    @Override
    public void onPostRemoved() {
        adapter.deleteSelectedItem();
    }

    @Override
    public void onUpdatedPost(Post post) {
        adapter.updateItem(post);
        navigateToDescriptionActivity(post);
    }

    @Override
    public void onMessage(String message) {
        showMessage(message);
    }

    @Override
    public void onShowLoader(final boolean show) {
        loader.post(() -> loader.setVisibility(show ? View.VISIBLE : View.GONE));
    }

    //Adapter actions
    @Override
    public void onClickItem(Post post) {
        mainPresenter.changeInitialState(post);

    }

    @Override
    public void onRemoveItem(String title) {
        showUndoDeleteSnackBar(title);
    }


    //view actions
    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.coordinator_Layout), message,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    private void showUndoDeleteSnackBar(String title) {
        String label = String.format(getString(R.string.item_removed_placeholder), title);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_Layout), label, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo_delete_label, v -> adapter.undoDeleteItem());
        snackbar.show();
    }

    private void navigateToDescriptionActivity(Post post) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_POST_KEY, post);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (intent != null && intent.getExtras() != null) {
                Post post = intent.getExtras().getParcelable(Constants.SELECTED_POST_KEY);
                if (post != null) {
                    recyclerView.post(() -> adapter.updateItem(post));
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            listState = recyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(Constants.PARCELABLE_STATE_LIST, listState);
        }
        super.onSaveInstanceState(outState);
    }

}
