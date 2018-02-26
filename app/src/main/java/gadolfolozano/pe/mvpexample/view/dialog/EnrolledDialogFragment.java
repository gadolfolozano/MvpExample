package gadolfolozano.pe.mvpexample.view.dialog;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.DialogFragmentEnrolledBinding;

/**
 * Created by gustavo.lozano on 2/26/2018.
 */

public class EnrolledDialogFragment extends DialogFragment {

    private static final String EVENT_ID = "event_id";

    private DialogFragmentEnrolledBinding mBinding;

    public static EnrolledDialogFragment newInstance(String eventId) {
        EnrolledDialogFragment f = new EnrolledDialogFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, eventId);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_enrolled, container, false);
        formatWindow();
        return mBinding.getRoot();
    }

    public void formatWindow() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
    }
}
