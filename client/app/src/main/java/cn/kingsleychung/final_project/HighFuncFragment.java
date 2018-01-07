package cn.kingsleychung.final_project;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HighFuncFragment extends Fragment {
    private View HighFuncView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        HighFuncView = inflater.inflate(R.layout.activity_high_func, container, false);
        return HighFuncView;
    }
}
