package cn.kingsleychung.final_project.Other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Kings on 2017/12/13.
 */

public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.canaroExtraBold);
    }

}
