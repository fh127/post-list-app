package com.example.post.list.app.presentation.presenters;

import com.example.post.list.app.business.FilterMenuUseCase;
import com.example.post.list.app.model.repository.PostsContractRepository;
import com.example.post.list.app.model.persistence.entities.Post;

import java.util.HashMap;
import java.util.List;

import static com.example.post.list.app.utils.Constants.*;
import static com.example.post.list.app.utils.Constants.ALL_OPTION;

/**
 * @author fabian hoyos
 */
public class MainPresenterImpl extends BasePresenter<PostContractMainView.View> implements PostContractMainView.Presenter, PostsContractRepository.ModelOperations {

    private PostsContractRepository.Repository repository;
    private FilterMenuUseCase filterMenuUseCase;
    private HashMap<Integer, FilterOptions> postFilterOptionsMap;

    public MainPresenterImpl(PostContractMainView.View view, PostsContractRepository.Repository repository, FilterMenuUseCase filterMenuUseCase) {
        super(view);
        this.repository = repository;
        this.filterMenuUseCase = filterMenuUseCase;
        init();
    }

    private void initMap() {
        postFilterOptionsMap = new HashMap<>();
        postFilterOptionsMap.put(UNKNOWN_OPTION, this::getAllPostList);
        postFilterOptionsMap.put(ALL_OPTION, this::getAllPostList);
        postFilterOptionsMap.put(FAVORITE_OPTION, this::getFavoritePostList);
    }

    @Override
    public void init() {
        super.init();
        repository.init(this);
        initMap();
    }

    @Override
    public void refreshList() {
        repository.refreshPost();
    }

    @Override
    public void getPostList() {
        view.onShowLoader(true);
        int optionId = filterMenuUseCase.getSelectedFilterOptionId();
        FilterOptions executor = postFilterOptionsMap.get(optionId);
        executor.executeFilter();
    }

    @Override
    public void removePost(Post post) {
        repository.removePost(post.getId());
    }

    @Override
    public void removePosts() {
        repository.removeAllPosts();
    }

    @Override
    public void changeInitialState(Post post) {
        if (post.getState() == INITIAL_SELECTION) {
            post.setState(NONE);
            repository.updatePost(post);
        } else
            view.onUpdatedPost(post);

    }

    @Override
    public void setFilterMenuOption(int id, String title) {
        filterMenuUseCase.setSelectedFilterOption(id, title);
    }

    @Override
    public String getFilterMenuOption(String defaultTitle) {
        return filterMenuUseCase.getSelectedFilterOption(defaultTitle);
    }

    @Override
    public void onPostList(List<Post> postList) {
        view.onPostList(postList);
    }

    @Override
    public void onRemovedPost() {
        view.onPostRemoved();
    }

    @Override
    public void onRemovedPosts() {
        view.onPostListRemoved();
    }

    @Override
    public void onPost(Post post) {
        view.onUpdatedPost(post);
    }

    @Override
    public void onError(String message) {
        view.onShowLoader(false);
        view.onMessage(message);
    }

    @Override
    public void onRefreshList() {
        view.onRefresh();
    }

    @Override
    public void release() {
        super.release();
        repository.release();
    }


    private void getAllPostList() {
        repository.getPosts();
    }

    private void getFavoritePostList() {
        view.onShowLoader(true);
        repository.getPostsByState(FAVORITE);
    }

    /**
     * Functional interface to handle filter action for repository operation
     */
    @FunctionalInterface
    interface FilterOptions {
        void executeFilter();
    }
}
