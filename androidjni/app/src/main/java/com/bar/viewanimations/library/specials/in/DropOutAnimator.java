package com.bar.viewanimations.library.specials.in;

import android.view.View;

import com.bar.viewanimations.library.BaseViewAnimator;
import com.bar.viewanimations.easing.Glider;
import com.bar.viewanimations.easing.Skill;
import com.bar.nineoldandroids.animation.ObjectAnimator;

public class DropOutAnimator extends BaseViewAnimator{
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
    }
}
