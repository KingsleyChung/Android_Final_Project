package cn.kingsleychung.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kings on 2017/12/23.
 */

public class SigninSignup extends Activity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ConstraintLayout mSigninSignup;
    private TextView mSigninTitle, mSignupTitle;
    private EditText mSigninUsername, mSigninPassword, mSignupUsername, mSignupPassword, mSignupConfirmPassword, mSignupEmail, mSignupPhoneNo;
    private TextInputLayout mSigninUsernameLayout, mSigninPasswordLayout, mSignupUsernameLayout, mSignupPasswordLayout, mSignupConfirmPasswordLayout, mSignupEmailLayout, mSignupPhoneNoLayout;
    private ImageView mIcon;
    private Button mSignin, mSigninRegister, mSignupRegister, mCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup);

        mSharedPreferences = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        initStatus();
    }

    private void initStatus() {
        if (mSharedPreferences.getString("password", null) == null) {
            initView();
            initClickListener();
        } else {
            enterApp();
        }
    }

    private void enterApp() {
        Intent intent = new Intent(SigninSignup.this, MainActivity.class);
        intent.putExtra("username", mSharedPreferences.getString("username", null));
        startActivity(intent);
    }

    private void initView() {
        mSigninSignup = findViewById(R.id.signin_signup);
        mSigninTitle = findViewById(R.id.sign_in);
        mSigninUsername = findViewById(R.id.signin_username_input);
        mSigninPassword = findViewById(R.id.signin_password_input);
        mSigninUsernameLayout = findViewById(R.id.signin_username_inputLayout);
        mSigninPasswordLayout = findViewById(R.id.signin_password_inputLayout);
        mSignupTitle = findViewById(R.id.sign_up);
        mSignupUsername = findViewById(R.id.signup_username_input);
        mSignupPassword = findViewById(R.id.signup_password_input);
        mSignupConfirmPassword = findViewById(R.id.signup_confirmPassword_input);
        mSignupEmail = findViewById(R.id.signup_email_input);
        mSignupPhoneNo = findViewById(R.id.signup_phoneNo_input);
        mSignupUsernameLayout = findViewById(R.id.signup_username_inputLayout);
        mSignupPasswordLayout = findViewById(R.id.signup_password_inputLayout);
        mSignupConfirmPasswordLayout = findViewById(R.id.signup_confirmPassword_inputLayout);
        mSignupEmailLayout = findViewById(R.id.signup_email_inputLayout);
        mSignupPhoneNoLayout = findViewById(R.id.signup_phoneNo_inputLayout);
        mIcon = findViewById(R.id.signup_icon);
        mSignin = findViewById(R.id.signin_btn);
        mSigninRegister = findViewById(R.id.signin_register_btn);
        mSignupRegister = findViewById(R.id.signup_register_btn);
        mCancel = findViewById(R.id.signup_cancel_btn);
        showSignin();
        hideSignup();
    }

    private void initClickListener() {
        mSigninSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = mSigninUsername.getText().toString();
                String inputPassword = mSigninPassword.getText().toString();
                boolean check = true;
                if (inputUsername.equals("")) {
                    mSigninUsernameLayout.setError(getResources().getText(R.string.emptyusername));
                    check = false;
                }
                if (inputPassword.equals("")) {
                    mSigninPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    check = false;
                }
                if (check && authenticateAccount(inputUsername, inputPassword)) {
                    mEditor.putString("username", inputUsername);
                    mEditor.putString("password", inputPassword);
                    mEditor.commit();
                    enterApp();
                }
            }
        });

        mSigninRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSignin();
                showSignup();
            }
        });

        mSignupRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = mSignupUsername.getText().toString();
                String inputPassword = mSignupPassword.getText().toString();
                String inputConfirmPassword = mSignupConfirmPassword.getText().toString();
                String inputEmail = mSignupEmail.getText().toString();
                String inputPhoneNo = mSignupPhoneNo.getText().toString();
                boolean check = true;
                if (inputUsername.equals("")) {
                    mSignupUsernameLayout.setError(getResources().getText(R.string.emptyusername));
                    check = false;
                }
                if (inputPassword.equals("")) {
                    mSignupPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    check = false;
                }
                if (inputConfirmPassword.equals("")) {
                    mSignupConfirmPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    check = false;
                }
                if (inputEmail.equals("")) {
                    mSignupEmailLayout.setError(getResources().getText(R.string.emptyemail));
                    check = false;
                }
                if (inputPhoneNo.equals("")) {
                    mSignupPhoneNoLayout.setError(getResources().getText(R.string.emptyphoneno));
                    check = false;
                }
                if (check && registerAccount(inputUsername, inputPassword, inputEmail, inputPhoneNo)) {
                    mEditor.putString("username", inputUsername);
                    mEditor.putString("password", inputPassword);
                    mEditor.commit();
                    enterApp();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSignup();
                showSignin();
            }
        });
    }

    private boolean authenticateAccount(String username, String password) {
        //联网验证用户名和密码
        return false;
    }

    private boolean registerAccount(String username, String password, String email, String phoneNo) {
        //联网注册用户
        return false;
    }

    private void hideSignin() {
        mSigninTitle.setVisibility(View.INVISIBLE);
        mSigninUsername.setVisibility(View.INVISIBLE);
        mSigninPassword.setVisibility(View.INVISIBLE);
        mSigninUsernameLayout.setVisibility(View.INVISIBLE);
        mSigninPasswordLayout.setVisibility(View.INVISIBLE);
        mSigninRegister.setVisibility(View.INVISIBLE);
        mSignin.setVisibility(View.INVISIBLE);
    }

    private void showSignin() {
        mSigninTitle.setVisibility(View.VISIBLE);
        mSigninUsername.setVisibility(View.VISIBLE);
        mSigninPassword.setVisibility(View.VISIBLE);
        mSigninUsernameLayout.setVisibility(View.VISIBLE);
        mSigninPasswordLayout.setVisibility(View.VISIBLE);
        mSigninRegister.setVisibility(View.VISIBLE);
        mSignin.setVisibility(View.VISIBLE);
    }

    private void hideSignup() {
        mSignupTitle.setVisibility(View.INVISIBLE);
        mIcon.setVisibility(View.INVISIBLE);
        mSignupUsername.setVisibility(View.INVISIBLE);
        mSignupPassword.setVisibility(View.INVISIBLE);
        mSignupConfirmPassword.setVisibility(View.INVISIBLE);
        mSignupPhoneNo.setVisibility(View.INVISIBLE);
        mSignupEmail.setVisibility(View.INVISIBLE);
        mSignupUsernameLayout.setVisibility(View.INVISIBLE);
        mSignupPasswordLayout.setVisibility(View.INVISIBLE);
        mSignupConfirmPasswordLayout.setVisibility(View.INVISIBLE);
        mSignupPhoneNoLayout.setVisibility(View.INVISIBLE);
        mSignupEmailLayout.setVisibility(View.INVISIBLE);
        mSignupRegister.setVisibility(View.INVISIBLE);
        mCancel.setVisibility(View.INVISIBLE);
    }

    private void showSignup() {
        mSignupTitle.setVisibility(View.VISIBLE);
        mIcon.setVisibility(View.VISIBLE);
        mSignupUsername.setVisibility(View.VISIBLE);
        mSignupPassword.setVisibility(View.VISIBLE);
        mSignupConfirmPassword.setVisibility(View.VISIBLE);
        mSignupPhoneNo.setVisibility(View.VISIBLE);
        mSignupEmail.setVisibility(View.VISIBLE);
        mSignupUsernameLayout.setVisibility(View.VISIBLE);
        mSignupPasswordLayout.setVisibility(View.VISIBLE);
        mSignupConfirmPasswordLayout.setVisibility(View.VISIBLE);
        mSignupPhoneNoLayout.setVisibility(View.VISIBLE);
        mSignupEmailLayout.setVisibility(View.VISIBLE);
        mSignupRegister.setVisibility(View.VISIBLE);
        mCancel.setVisibility(View.VISIBLE);
    }
}
