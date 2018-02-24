package flatstack.com.roomarchcomponents.screens;

import android.support.annotation.LayoutRes;

public class ActivityInfo {

    @LayoutRes private final int layoutId;

    public ActivityInfo(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
}
