package cn.kingsleychung.final_project;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kingsleychung.final_project.User.UserClass;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

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
    private ProgressBar mProgress;
    private UserManagement mUserManagement;

    private boolean mIconUploadStatus;
    private String mIconTempName;
    private File mUploadPic;

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_FILE_NAME = "upload_pic.png";
    private static String APP_ROOT_DIR;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup);

        mSharedPreferences = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        initDir();
        initStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermissions();
    }

    private void initPermissions() {
        PermissionChecker mPermissionsChecker = new PermissionChecker(this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, 0, PERMISSIONS);
        }
    }

    private void initDir() {
        APP_ROOT_DIR = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name);
        mUploadPic = new File(APP_ROOT_DIR + "/temp/", PHOTO_FILE_NAME);
        if (!mUploadPic.exists()) {
            mUploadPic.mkdirs();
        }
    }

    private void initStatus() {
        initView();
        if (mSharedPreferences.getString("username", null) != null && mSharedPreferences.getString("password", null) != null) {
            login(mSharedPreferences.getString("username", null), mSharedPreferences.getString("password", null));
        } else {
            initClickListener();
            initInputListener();
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
        mProgress = findViewById(R.id.signin_signup_progress);

        initEditTextErrorMessage();

        mUserManagement = UserManagement.getInstance();
        setSigninVisibility(View.VISIBLE);
        setSignupVisibility(View.INVISIBLE);
        stopWaiting();
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

                if (checkUsername(mSigninUsername) == 1 && checkPassword(mSigninPassword) == 1) {
                    login(inputUsername, inputPassword);
                }
            }
        });

        mSigninRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSigninVisibility(View.INVISIBLE);
                setSignupVisibility(View.VISIBLE);
            }
        });

        mSignupRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = mSignupUsername.getText().toString();
                String inputPassword = mSignupPassword.getText().toString();
                String inputEmail = mSignupEmail.getText().toString();
                String inputPhoneNo = mSignupPhoneNo.getText().toString();
                if (!mIconUploadStatus) {
                    Toast.makeText(SigninSignup.this, getString(R.string.invalidicon), Toast.LENGTH_SHORT).show();
                } else if (registerCheck()) {
                    Subscriber<UserClass> registerSubscriber = (new Subscriber<UserClass>() {
                        @Override
                        public void onCompleted() {
                            stopWaiting();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(SigninSignup.this, R.string.networkerror,Toast.LENGTH_SHORT).show();
                            stopWaiting();
                        }

                        @Override
                        public void onNext(UserClass user) {
                            //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                            UserManagement.getInstance().storeUser(user);
                            if (user.getSuccess()) {
                                //login(user.getUserName(), user.getPassword());
                            } else {
                                Toast.makeText(SigninSignup.this, user.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    UserClass newUser = new UserClass(inputUsername, null, inputPassword, inputPhoneNo, inputEmail, null, null,null);
                    waiting();
                    mUserManagement.register(mUploadPic.getPath().toString(), newUser, registerSubscriber);
                } else {
                    Toast.makeText(SigninSignup.this, getString(R.string.inputvalidinfo), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEditTextErrorMessage();
                setSignupVisibility(View.INVISIBLE);
                setSigninVisibility(View.VISIBLE);
            }
        });

        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromGallery();
                //takeFromCamera();
            }
        });
    }

    private void initInputListener() {
        mSigninUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSigninUsernameLayout.setError("");
                } else {
                    if (checkUsername(mSigninUsername) == 0) {
                        mSigninUsernameLayout.setError(getResources().getText(R.string.emptyusername));
                    }
                    else if (checkUsername(mSigninUsername) == 2) {
                        mSigninUsernameLayout.setError(getResources().getText(R.string.invalidusername));
                    }
                }
            }
        });

        mSigninPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSigninPasswordLayout.setError("");
                } else {
                    if (checkPassword(mSigninPassword) == 0) {
                        mSigninPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    };
                }
            }
        });

        mSignupUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSignupUsernameLayout.setError("");
                } else {
                    if (checkUsername(mSignupUsername) == 0) {
                        mSignupUsernameLayout.setError(getResources().getText(R.string.emptyusername));
                    }
                    else if (checkUsername(mSignupUsername) == 2) {
                        mSignupUsernameLayout.setError(getResources().getText(R.string.invalidusername));
                    }
                }
            }
        });

        mSignupPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSignupPasswordLayout.setError("");
                } else {
                    if (checkUsername(mSignupPassword) == 0) {
                        mSignupPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    }
                    else if (checkConfirmPassword() == 1) {
                        mSignupConfirmPasswordLayout.setError("");
                    }
                }
            }
        });

        mSignupConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSignupConfirmPasswordLayout.setError("");
                } else {
                    if (checkConfirmPassword() == 0) {
                        mSignupConfirmPasswordLayout.setError(getResources().getText(R.string.emptypassword));
                    }
                    else if (checkConfirmPassword() == 2) {
                        mSignupConfirmPasswordLayout.setError(getResources().getText(R.string.mismatch));
                    }
                }
            }
        });

        mSignupEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSignupEmailLayout.setError("");
                } else {
                    if (checkEmail() == 0) {
                        mSignupEmailLayout.setError(getResources().getText(R.string.emptyemail));
                    }
                    else if (checkEmail() == 2) {
                        mSignupEmailLayout.setError(getResources().getText(R.string.invalidemail));
                    }
                }
            }
        });

        mSignupPhoneNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSignupPhoneNoLayout.setError("");
                } else {
                    if (checkPhoneNo() == 0) {
                        mSignupPhoneNoLayout.setError(getResources().getText(R.string.emptyphoneno));
                    }
                    else if (checkPhoneNo() == 2) {
                        mSignupPhoneNoLayout.setError(getResources().getText(R.string.invalidphoneno));
                    }
                }
            }
        });
    }

    private void initEditTextErrorMessage() {
        mSigninUsernameLayout.setError("");
        mSigninPasswordLayout.setError("");
        mSignupUsernameLayout.setError("");
        mSignupPasswordLayout.setError("");
        mSignupConfirmPasswordLayout.setError("");
        mSignupEmailLayout.setError("");
        mSignupPhoneNoLayout.setError("");
    }

    private void login(String username, String password) {
        Subscriber<UserClass> loginSubscriber = (new Subscriber<UserClass>() {
            @Override
            public void onCompleted() {
                stopWaiting();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SigninSignup.this, R.string.networkerror,Toast.LENGTH_SHORT).show();
                stopWaiting();
            }

            @Override
            public void onNext(UserClass user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeUser(user);
                if (user.getSuccess()) {
                    mEditor.putString("username", user.getUserName());
                    mEditor.putString("password", user.getPassword());
                    mEditor.commit();
                    System.out.println(mSharedPreferences.getString("username", null));
                    System.out.println(mSharedPreferences.getString("password", null));
                    enterApp();
                } else {
                    Toast.makeText(SigninSignup.this, R.string.wrongusernameorpassword,Toast.LENGTH_SHORT).show();
                }
            }
        });
        waiting();
        mUserManagement.login(username, password, loginSubscriber);
    }

    private int checkUsername(EditText usernameInput) {
        String username = usernameInput.getText().toString();
        if (username.equals("")) return 0;
        if (username.contains(" ")) return 2;
        return 1;
    }

    private int checkPassword(EditText passwordInput) {
        String password = passwordInput.getText().toString();
        if (password.equals("")) return 0;
        return 1;
    }

    private int checkConfirmPassword() {
        String confirmPassword = mSignupConfirmPassword.getText().toString();
        if (confirmPassword.equals("")) return 0;
        if (!confirmPassword.equals(mSignupPassword.getText().toString())) return 2;
        return 1;
    }

    private int checkPhoneNo() {
        String phoneNo = mSignupPhoneNo.getText().toString();
        if (phoneNo.equals("")) return 0;
        Pattern p = Pattern.compile("/13[123569]{1}\\d{8}|15[1235689]\\d{8}|188\\d{8}/");
        Matcher m = p.matcher(phoneNo);
        if (!m.matches()) return 2;
        return 1;
    }

    private int checkEmail() {
        String email = mSignupEmail.getText().toString();
        if (email.equals("")) return 0;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        if (!m.matches()) return 2;
        return 1;
    }

    private boolean registerCheck() {
        mSignupUsername.requestFocus();
        mSignupPassword.requestFocus();
        mSignupConfirmPassword.requestFocus();
        mSignupPhoneNo.requestFocus();
        mSignupEmail.requestFocus();
        mSignupEmail.clearFocus();

        if (checkUsername(mSignupUsername) == 1 && checkPassword(mSignupPassword) == 1 && checkConfirmPassword() == 1 && checkEmail() == 1 && checkPhoneNo() == 1)
            return true;
        else
            return false;
    }

    private void setSigninVisibility(int visibility) {
        mSigninTitle.setVisibility(visibility);
        mSigninUsername.setVisibility(visibility);
        mSigninPassword.setVisibility(visibility);
        mSigninUsernameLayout.setVisibility(visibility);
        mSigninPasswordLayout.setVisibility(visibility);
        mSigninRegister.setVisibility(visibility);
        mSignin.setVisibility(visibility);
    }

    private void setSignupVisibility(int visibility) {
        mSignupTitle.setVisibility(visibility);
        mIcon.setVisibility(visibility);
        mSignupUsername.setVisibility(visibility);
        mSignupPassword.setVisibility(visibility);
        mSignupConfirmPassword.setVisibility(visibility);
        mSignupPhoneNo.setVisibility(visibility);
        mSignupEmail.setVisibility(visibility);
        mSignupUsernameLayout.setVisibility(visibility);
        mSignupPasswordLayout.setVisibility(visibility);
        mSignupConfirmPasswordLayout.setVisibility(visibility);
        mSignupPhoneNoLayout.setVisibility(visibility);
        mSignupEmailLayout.setVisibility(visibility);
        mSignupRegister.setVisibility(visibility);
        mCancel.setVisibility(visibility);
    }

    private void stopWaiting() {
        mProgress.setVisibility(View.GONE);
    }

    private void waiting() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                // 从相机返回的数据
                crop(Uri.fromFile(mUploadPic));
                break;
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri);
                }
                break;
            case PHOTO_REQUEST_CUT:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    saveBitmap(bitmap);
                    this.mIcon.setImageBitmap(bitmap);
                    mIconUploadStatus = true;
                    //uploadIcon();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectFromGallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void takeFromCamera() {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 从文件中创建uri
        Uri uri = Uri.fromFile(mUploadPic);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "PNG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void saveBitmap(Bitmap bm) {
        if (mUploadPic.exists()) {
            mUploadPic.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(mUploadPic);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void uploadIcon() {
        Subscriber<UserClass> uploadIconSubscriber = (new Subscriber<UserClass>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mIconUploadStatus = false;
                Toast.makeText(SigninSignup.this, "Icon upload failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(UserClass userClass) {
                mIconUploadStatus = true;
                mIconTempName = userClass.getIconName();
                Toast.makeText(SigninSignup.this, "Icon upload succeed.Name: " + mIconTempName , Toast.LENGTH_SHORT).show();
            }
        });
        UserManagement.getInstance().uploadPhoto(mUploadPic.getPath().toString(), uploadIconSubscriber);
    }
}