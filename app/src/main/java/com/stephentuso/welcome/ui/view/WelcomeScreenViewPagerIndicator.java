package com.stephentuso.welcome.ui.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by stephentuso on 11/16/15.
 * An extension of {@link SimpleViewPagerIndicator SimpleViewPagerIndicator} that implements
 * the setup method of {@link com.stephentuso.welcome.ui.OnWelcomeScreenPageChangeListener OnWelcomeScreenPageChangeListener}
 */
public class WelcomeScreenViewPagerIndicator extends SimpleViewPagerIndicator implements com.stephentuso.welcome.ui.OnWelcomeScreenPageChangeListener {

    public WelcomeScreenViewPagerIndicator(Context context) {
        super(context);
    }

    public WelcomeScreenViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeScreenViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setup(com.stephentuso.welcome.util.WelcomeScreenConfiguration config) {
        setTotalPages(config.viewablePageCount());
    }
}
