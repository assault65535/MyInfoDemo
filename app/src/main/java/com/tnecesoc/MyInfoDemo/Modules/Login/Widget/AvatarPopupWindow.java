package com.tnecesoc.MyInfoDemo.Modules.Login.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import com.tnecesoc.MyInfoDemo.R;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class AvatarPopupWindow extends PopupWindow {

    private Button btn_take_photo;
    private Button btn_choose_from_gallery;
    private Button btn_cancel;

    private View mMenuView;

    public AvatarPopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_demo, null);
    }
}
