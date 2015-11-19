package www.nuaa.edu.cn.inanhang.ViewFragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.ViewActivity.IntroActivity;

/**
 * 引导页
 *
 * @author sun
 * @version 15/8/27
 */
public class IntroFragment extends Fragment {

    private int mImgRes;
    private boolean mDone;

    public static IntroFragment newInstance(@DrawableRes int imgId) {
        return newInstance(imgId, false);
    }

    public static IntroFragment newInstance(@DrawableRes int imgId, boolean done) {
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("img", imgId);
        bundle.putBoolean("done", done);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mImgRes = getArguments().getInt("img");
        this.mDone = getArguments().getBoolean("done");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((ImageView) view.findViewById(R.id.image)).setImageResource(mImgRes);
        if (mDone) {
            Button btn = (Button) view.findViewById(R.id.done_btn);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(v -> ((IntroActivity) getActivity()).onDonePressed());
        }
    }
}
