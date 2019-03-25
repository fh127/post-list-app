package com.example.post.list.app.business;

import com.example.post.list.app.utils.PreferenceUtils;
import com.google.common.base.Strings;

import javax.inject.Inject;

import static com.example.post.list.app.utils.Constants.MENU_ITEM_ID;
import static com.example.post.list.app.utils.Constants.MENU_ITEM_OPTION;

/**
 * Implementation of {@link java.util.logging.Filter
 *
 * @author fabian hoyos
 */
public class FilterMenuHandler implements FilterMenuUseCase {

    private PreferenceUtils preferenceUtils;

    @Inject
    public FilterMenuHandler(PreferenceUtils preferenceUtils) {
        this.preferenceUtils = preferenceUtils;
    }

    @Override
    public int getSelectedFilterOptionId() {
        return preferenceUtils.getIntValue(MENU_ITEM_ID);
    }

    @Override
    public String getSelectedFilterOption(String defaultTitle) {
        String selectedFilterOption = preferenceUtils.getStringValue(MENU_ITEM_OPTION);
        return Strings.isNullOrEmpty(selectedFilterOption) ? defaultTitle : selectedFilterOption;
    }

    @Override
    public void setSelectedFilterOption(int id, String title) {
        preferenceUtils.setIntValue(MENU_ITEM_ID, id);
        preferenceUtils.setStringValue(MENU_ITEM_OPTION, title);
    }
}
