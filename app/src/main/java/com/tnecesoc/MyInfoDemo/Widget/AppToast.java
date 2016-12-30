package com.tnecesoc.MyInfoDemo.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.R;

/**
 * Created by Tnecesoc on 2016/12/22.
 */
@Deprecated
public class AppToast extends Toast {

    public AppToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast result = new Toast(context);

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View v = inflate.inflate(R.layout.app_toast, null, false);
        TextView tv = (TextView)v.findViewById(android.R.id.message);
        tv.setText(text);

        result.setView(v);
        result.setDuration(duration);

        return result;
    }
}
