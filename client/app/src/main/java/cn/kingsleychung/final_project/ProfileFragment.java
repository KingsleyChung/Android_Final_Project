package cn.kingsleychung.final_project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

public class ProfileFragment extends Fragment{
    private View ProfileView;
    private ImageView imageView;
    private TextView textView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ProfileView = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        imageView = ProfileView.findViewById(R.id.profilePhoto);
        textView = ProfileView.findViewById(R.id.profileName);
        String username = UserManagement.getInstance().getUser().getUserName() + ".jpg";
        textView.setText(UserManagement.getInstance().getUser().getUserName());
        UserManagement.getInstance().getPhoto(username, new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        return ProfileView;
    }
}
