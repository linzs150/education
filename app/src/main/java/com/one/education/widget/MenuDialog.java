package com.one.education.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.education.R;
import com.one.education.utils.Utilts;

/**
 * Copyright (c) 2015, Intretech All rights reserved.
 *
 * @Description
 * @author zhuangjianwei
 * @email fzhzjw@intretech.com
 * @date Sep 1, 2015
 * @version v1.0
 */
public class MenuDialog extends PushUpInDialog {
    private IOnSelectedMenuListener mMenuListener = null;

    public MenuDialog(Context context, MenuOption menuOption) {
        super(context, R.layout.dialog_menu);

        LinearLayout viewContent = findViewById(R.id.content);
        View menuView;
        TextView textView;
        int position = 0;
        int size = menuOption.size();
        for (int index = 0; index < size; index++) {
            if (0 != index) {
                //菜单之间的分割线
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) Utilts.GetDimension(mContext, R.dimen.px_1));

                ImageView separator = new ImageView(context);
                separator.setBackgroundColor(context.getResources().getColor(R.color.color_928d8d));
                separator.setLayoutParams(params);
                viewContent.addView(separator);
            }

            MenuOption.MenuItem menuItem = menuOption.get(index);
            if (MenuOption.MENU_TYPE_ITEM == menuItem.type) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) Utilts.GetDimension(context, R.dimen.dp_50));

                textView = new TextView(context);
                textView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(context.getResources().getColor(-1 == menuItem.textColor ? R.color.color_666666 : menuItem.textColor));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.dp_16));
                textView.setText(menuItem.name);
                textView.setLayoutParams(params);
                textView.setTag(new Object[]{position++, menuItem.key});
                textView.setOnClickListener(mOnClickListener);
                menuView = textView;
            } else if (MenuOption.MENU_TYPE_SEPARATOR == menuItem.type) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) Utilts.GetDimension(context, R.dimen.dp_5));
                params.topMargin = (int) Utilts.GetDimension(mContext, R.dimen.px_1);

                menuView = new View(context);
                menuView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                menuView.setLayoutParams(params);
            } else {
                continue;
            }

            viewContent.addView(menuView);
        }
    }

    public void setMenuListener(IOnSelectedMenuListener listener) {
        mMenuListener = listener;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Object[] params = (Object[]) arg0.getTag();
            if (null != mMenuListener && mMenuListener.onSelectedMenu((Integer) params[0], (Integer) params[1])) {
                return;
            }

            dismiss();
        }
    };

    public interface IOnSelectedMenuListener {
        /**
         * 选择菜单
         * @param position 位置
         * @param key 内容标识
         * @return 选择结果
         */
        boolean onSelectedMenu(int position, int key);
    }
}
