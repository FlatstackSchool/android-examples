package flatstack.com.roomarchcomponents.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    protected abstract ActivityInfo getActivityInfo();

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private Unbinder bindings;

    public Disposable subscribe(Disposable disposable) {
        subscriptions.add(disposable);
        return disposable;
    }

    @Override public View onCreateView(LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getActivityInfo().getLayoutId(), container, false);
        bindings = ButterKnife.bind(this, v);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        bindings.unbind();
    }

    @Override public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

}
