package com.example.haryono.workout.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Haryono on 5/26/2017.
 */

public class CustomTextView3 extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomTextView3(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomTextView3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("Gotham-Medium_0.otf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("Gotham-Medium_0.otf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface("Gotham-Medium_0.otf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("Gotham-Medium_0.otf", context);
        }
    }
}