package com.one.education.widget;

import com.one.education.education.R;

import java.util.List;
import java.util.Vector;

/**
 * MenuOption:
 *
 * @author lyy
 * @version v1.0 2017/12/23
 */
public class MenuOption {
    static final int MENU_TYPE_ITEM = 1;
    static final int MENU_TYPE_SEPARATOR = 2;

    private final List<MenuItem> mMenuItems;

    private MenuOption(List<MenuItem> menuItemIntervals) {
        mMenuItems = menuItemIntervals;
    }

    int size() {
        return null == mMenuItems ? 0 : mMenuItems.size();
    }

    MenuItem get(int index) {
        return mMenuItems.get(index);
    }

    static class MenuItem {
        int type;
        String name;
        int key;
        int textColor;
    }

    public static class MenuOptionBuilder {
        private List<MenuItem> mMenuItems = new Vector<>();

        private MenuOptionBuilder() {
        }

        public static MenuOptionBuilder newBuilder() {
            return new MenuOptionBuilder();
        }

        /**
         * 添加一个选项菜单<br/>
         * 该菜单颜色为黑色
         */
        public void addOptionItem(String name) {
            addOptionItem(name, 0);
        }

        /**
         * 添加一个菜单<br/>
         * 该菜单颜色为黑色
         */
        public void addOptionItem(String name, int key) {
            addItem(name, key, R.color.color_666666);
        }

        /**
         * 添加一个带警告颜色菜单<br/>
         * 该菜单颜色为红色
         */
        public void addWarningItem(String name) {
            addWarningItem(name, 0);
        }

        /**
         * 添加一个带警告颜色菜单<br/>
         * 该菜单颜色为红色
         */
        public void addWarningItem(String name, int key) {
            addItem(name, key, R.color.color_666666);
        }

        /**
         * 添加一个带次要颜色菜单<br/>
         * 该菜单颜色为灰色
         */
        public void addSecondaryItem(String name) {
            addSecondaryItem(name, 0);
        }

        /**
         * 添加一个带次要颜色菜单<br/>
         * 该菜单颜色为灰色
         */
        public void addSecondaryItem(String name, int key) {
            addItem(name, key, R.color.color_666666);
        }

        /**
         * 添加选项
         */
        public void addItem(String name, int key, int textColor) {
            MenuItem menuItem = new MenuItem();
            menuItem.type = MENU_TYPE_ITEM;
            menuItem.name = name;
            menuItem.key = key;
            menuItem.textColor = textColor;
            mMenuItems.add(menuItem);
        }

        /**
         * 添加分割符
         */
        public void addSeparator() {
            MenuItem menuItem = new MenuItem();
            menuItem.type = MENU_TYPE_SEPARATOR;
            mMenuItems.add(menuItem);
        }

        public MenuOption build() {
            MenuOption menuOption = new MenuOption(mMenuItems);
            mMenuItems = null;
            return menuOption;
        }
    }
}
