package com.example.post.list.app.business;

/**
 * this use case defines the logic to handle filter options for post list
 *
 * @author fabian hoyos
 */
public interface FilterMenuUseCase {

    int getSelectedFilterOptionId();

    String getSelectedFilterOption(String defaultTitle);

    void setSelectedFilterOption(int id, String title);

}
