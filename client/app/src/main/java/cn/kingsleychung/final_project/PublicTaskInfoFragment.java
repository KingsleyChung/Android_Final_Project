package cn.kingsleychung.final_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kingsley on 6/1/2018.
 */

public class PublicTaskInfoFragment extends Fragment {

    private View mInfoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfoView = inflater.inflate(R.layout.public_task_info, container, false);

        return mInfoView;
    }
}
