package com.bar.viewanimations.library.specials.in;

import android.view.View;

import com.bar.viewanimations.library.BaseViewAnimator;
import com.bar.viewanimations.easing.Glider;
import com.bar.viewanimations.easing.Skill;
import com.bar.nineoldandroids.animation.ObjectAnimator;

public class LandingAnimator extends BaseViewAnimator{
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 0, 1f))
        );
    }
}
