package com.example.shibegallery;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * {@link android.view.View.OnClickListener} used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
public class NavigationIconClickListener implements View.OnClickListener {

    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private Interpolator interpolator;
    private int height;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;
    private OnBackDropListener listener;

    NavigationIconClickListener(Context context, View sheet) {
        this(context, sheet, null);
    }

    NavigationIconClickListener(Context context, View sheet, @Nullable Interpolator interpolator) {
        this(context, sheet, interpolator, null, null, null);
    }


    public NavigationIconClickListener(Context context, View sheet, @Nullable Interpolator interpolator, OnBackDropListener listener) {
        this(context, sheet, interpolator, listener, null, null);
    }

    public NavigationIconClickListener(
            Context context, View sheet, @Nullable Interpolator interpolator, @Nullable OnBackDropListener listener,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon) {
        this.context = context;
        this.sheet = sheet;
        this.interpolator = interpolator;
        this.listener = listener;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View view) {
        backdropShown = !backdropShown;
        listener.backDropToggled(backdropShown);

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        updateIcon(view);

        final int translateY = (int) (height - (height * .65));

        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();
    }

    private void updateIcon(View view) {
        if (openIcon != null && closeIcon != null) {
            if (!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (backdropShown) {
                ((ImageView) view).setImageDrawable(closeIcon);
            } else {
                ((ImageView) view).setImageDrawable(openIcon);
            }
        }
    }

    public interface OnBackDropListener {
        void backDropToggled(boolean isShown);
    }
}
