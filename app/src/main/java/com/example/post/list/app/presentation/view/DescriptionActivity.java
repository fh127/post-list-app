package com.example.post.list.app.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.post.list.app.R;
import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.presentation.adapter.CommentAdapter;
import com.example.post.list.app.presentation.adapter.ItemDecoration;
import com.example.post.list.app.presentation.presenters.BasePresenter;
import com.example.post.list.app.presentation.presenters.PostContractDescriptionView;
import com.example.post.list.app.utils.Constants;
import com.example.post.list.app.utils.ViewUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;

import static com.example.post.list.app.presentation.presenters.PostContractDescriptionView.Presenter;
import static com.example.post.list.app.utils.Constants.SELECTED_POST_KEY;

/**
 * @author fabian hoyos
 */
public class DescriptionActivity extends AppCompatActivity implements PostContractDescriptionView.View {

    @Inject
    Presenter descriptionPresenter;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private TextView title;
    private TextView description;
    private TextView userInfo;
    private ProgressBar loader;
    private FloatingActionButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = findViewById(R.id.toolbar_description);
        setSupportActionBar(toolbar);
        setupView();
        handleDescriptionContent();
    }

    private void setupLoaderView() {
        loader = findViewById(R.id.loader_view);
        loader.setVisibility(View.GONE);
    }

    // UI settigns
    private void setupView() {
        setupLoaderView();
        setupFavoriteButton();
        adapter = new CommentAdapter();
        recyclerView = findViewById(R.id.comments_recycler_view);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        userInfo = findViewById(R.id.user_info);
        final Context context = recyclerView.getContext();
        final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDecoration(spacing));
    }

    private void setupFavoriteButton() {
        favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(view -> descriptionPresenter.addPostToFavorites());
    }

    @Override
    protected void onDestroy() {
        ((BasePresenter) descriptionPresenter).release();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(SELECTED_POST_KEY, descriptionPresenter.getCurrentPost());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPost(Post post) {
        showPost(post);
        onShowLoader(false);
    }

    @Override
    public void onShowLoader(boolean show) {
        loader.post(() -> loader.setVisibility(show ? View.VISIBLE : View.GONE));
    }

    @Override
    public void onMessage(String message) {
        showMessage(message);
    }

    private void handleDescriptionContent() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Post post = intent.getExtras().getParcelable(Constants.SELECTED_POST_KEY);
            descriptionPresenter.getPost(post);
        }
    }

    private void showPost(Post post) {
        title.setText(post.getTitle());
        description.setText(post.getBody());
        userInfo.setText(post.getUser().toString());
        adapter.setComments(post.getComments());
        favoriteButton.setImageResource(ViewUtils.getResourceFavoriteState(post.getState()));
    }

    //view actions
    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.layout_container), message,
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
