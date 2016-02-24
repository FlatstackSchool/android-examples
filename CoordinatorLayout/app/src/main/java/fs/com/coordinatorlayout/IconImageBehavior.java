package fs.com.coordinatorlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ramil on 19/02/16.
 */
public class IconImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private float mStartXPosition;
    private float mStartToolbarPosition;
    private float mStartYPosition;
    private float mFinalYPosition;
    private float mFinalHeight;
    private float mStartHeight;
    private float mFinalXPosition;
    private float mStatusBarHeight;

    @SuppressLint("PrivateResource")
    public IconImageBehavior(Context context, AttributeSet attrs) {
        mFinalHeight = context.getResources().getDimensionPixelOffset(R.dimen.image_final_width);
        mFinalXPosition = context.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material)
                + (mFinalHeight / 2);

        mStatusBarHeight = getStatusBarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        initProperties(child, dependency);

        int maxScrollDistance = (int) (mStartToolbarPosition - mStatusBarHeight);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);

        float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                * (1f - expandedPercentageFactor)) + (child.getWidth() / 2);

        float heightToSubtract = ((mStartHeight - mFinalHeight) * (1f - expandedPercentageFactor));

        child.setY(mStartYPosition - distanceYToSubtract);
        child.setX(mStartXPosition - distanceXToSubtract);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = (int) (mStartHeight - heightToSubtract);
        lp.height = (int) (mStartHeight - heightToSubtract);
        child.setLayoutParams(lp);
        return true;
    }

    private void initProperties(CircleImageView child, View dependency) {
        if (mStartYPosition == 0)
            mStartYPosition = (int) (dependency.getY());

        if (mFinalYPosition == 0)
            mFinalYPosition = (dependency.getHeight() / 2);

        if (mStartHeight == 0)
            mStartHeight = child.getHeight();

        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY() + (dependency.getHeight() / 2);
    }

    public float getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId != 0) {
            return context.getResources()
                    .getDimensionPixelSize(resourceId);
        } else {
            return 50;
        }
    }
}
