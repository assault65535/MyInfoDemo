package com.tnecesoc.MyInfoDemo.Widget.Util;

import android.content.Context;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class DisplayUtil {

    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
