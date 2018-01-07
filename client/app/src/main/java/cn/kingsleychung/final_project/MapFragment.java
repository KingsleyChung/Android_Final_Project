package cn.kingsleychung.final_project;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.util.List;

import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by Kings on 2017/12/15.
 */

public class MapFragment extends Fragment implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    View mapView, mDrawer, mDrawerHolder;
    ConstraintLayout mInstructionBar;
    TextView mInstruction, mStartText, mEndText;
    EditText mTitle, mContent;
    AMap mAMap;
    MapView mMapView;
    MyLocationStyle myLocationStyle;
    UiSettings mUiSettings;
    ImageView mSubmit, mCancel, mStart, mEnd, mInstructionSubmit, mInstructionCancel;
    FrameLayout mDim;
    SlideUp mSlideUp;
    LatLng mStartLocation, mEndLocation;
    Marker mSelectedLocationMarker, mStartMarker, mEndMarker;
    GeocodeSearch geocoderSearch;
    int selectingMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mapView = inflater.inflate(R.layout.map, container, false);
        //获取地图控件引用
        mMapView = mapView.findViewById(R.id.map_display);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        initMap();
        initDrawer();
        return mapView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if(location != null) {
            UserManagement.getInstance().getUser().setLocation(new double[] { location.getLatitude(), location.getLongitude() });
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");

            }

        } else {
            Log.e("amap", "定位失败");
        }
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;
        myLocationStyle.interval(5000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(1000));
        mAMap.setOnMyLocationChangeListener(this);

        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);

        mSelectedLocationMarker = null;

        geocoderSearch = new GeocodeSearch(getContext());
        geocoderSearch.setOnGeocodeSearchListener(this);

        Subscriber<List<Task>> getNearTaskSubscriber = (new Subscriber<List<Task>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onNext(List<Task> tasks) {
                for (int i = 0; i < tasks.size(); i++) {
                    mAMap.addMarker(new MarkerOptions().position(new LatLng(tasks.get(i).getTaskPosLoc()[0], tasks.get(i).getTaskPosLoc()[1])));
                }
            }
        });
        UserManagement.getInstance().getNearTask(getNearTaskSubscriber);

        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                System.out.println("Marker has been clicked.");
                return  true;
            }
        });

        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (mSelectedLocationMarker != null) {
                    LatLng center = cameraPosition.target;
                    mSelectedLocationMarker.setPosition(center);
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLng center = cameraPosition.target;
                System.out.println(center.latitude + "   " + center.longitude);
            }
        });
    }

    private void initDrawer() {
        mDim = mapView.findViewById(R.id.map_dim);
        mDrawer = mapView.findViewById(R.id.slide_up_drawer);
        mDrawerHolder = mapView.findViewById(R.id.drawer_holder);
        mTitle = mapView.findViewById(R.id.drawer_title);
        mContent = mapView.findViewById(R.id.drawer_content);
        mSubmit = mapView.findViewById(R.id.drawer_submit);
        mCancel = mapView.findViewById(R.id.drawer_cancel);
        mStart = mapView.findViewById(R.id.drawer_start_location_icon);
        mEnd = mapView.findViewById(R.id.drawer_end_location_icon);
        mInstructionBar = mapView.findViewById(R.id.drawer_instruction_bar);
        mInstructionSubmit = mapView.findViewById(R.id.drawer_instruction_submit);
        mInstructionCancel = mapView.findViewById(R.id.drawer_instruction_cancel);
        mInstruction = mapView.findViewById(R.id.drawer_instruction_text);
        mStartText = mapView.findViewById(R.id.drawer_start_location_text);
        mEndText = mapView.findViewById(R.id.drawer_end_location_text);
        selectingMode = 0;

        mSlideUp = new SlideUpBuilder(mDrawer)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        mDim.setAlpha(1 - (percent / 100));
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                    }

                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withSlideFromOtherView(mapView.findViewById(R.id.map_fragment))
                .build();


        mDrawerHolder.setOnTouchListener(new View.OnTouchListener() {
            float mPosX = 0, mPosY = 0, mCurPosX = 0, mCurPosY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 25)) {
                            mSlideUp.show();
                        }
                        break;
                }
                return true;
            }
        });

        mDrawerHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideUp.show();
            }
        });

        initDrawerClickListener();
    }

    private void initDrawerClickListener() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBriefTask()) {
                    showDetailTaskActivity();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDrawer();
                mSlideUp.hide();
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectingMode = 1;
                enterSelectLocation(getResources().getString(R.string.starting_point));

            }
        });

        mEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectingMode = -1;
                enterSelectLocation(getResources().getString(R.string.destination));
            }
        });

        mInstructionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitSelectLocation();
            }
        });

        mInstructionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedLocationMarker != null) {
                    mSelectedLocationMarker.destroy();
                }
                exitSelectLocation();
            }
        });
    }

    private void enterSelectLocation(String point) {
        mSlideUp.hide();
        mInstructionBar.setVisibility(View.VISIBLE);
        mInstruction.setText(point);
        if (point.equals(getString(R.string.starting_point)) && mStartMarker != null) {
            mSelectedLocationMarker = mStartMarker;
        } else if (point.equals(getString(R.string.destination)) && mEndMarker != null) {
            mSelectedLocationMarker = mEndMarker;
        } else {
            mSelectedLocationMarker = mAMap.addMarker(new MarkerOptions().position(mAMap.getCameraPosition().target).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start_pin_down))));
        }
    }

    private void exitSelectLocation() {
        mSlideUp.show();
        mInstructionBar.setVisibility(View.GONE);
        if (mSelectedLocationMarker != null) {
            if (selectingMode == 1) {
                mStartLocation = mSelectedLocationMarker.getPosition();
                mStartMarker = mAMap.addMarker(new MarkerOptions().position(mStartLocation));
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(mStartLocation.latitude, mStartLocation.longitude), 0, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            }
            else if (selectingMode == -1) {
                mEndLocation = mSelectedLocationMarker.getPosition();
                mEndMarker = mAMap.addMarker(new MarkerOptions().position(mEndLocation));
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(mEndLocation.latitude, mEndLocation.longitude), 0, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            }
            mSelectedLocationMarker.destroy();
        }
    }

    private boolean checkBriefTask() {
        if (mTitle.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.emptytasktitle), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mStartLocation == null) {
            Toast.makeText(getContext(), getString(R.string.emptystartlocation), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mContent.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.emptycontent), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearDrawer() {
        mTitle.setText("");
        mStartText.setText(getString(R.string.click_to_locate));
        mEndText.setText(getString(R.string.click_to_locate));
        if (mStartMarker != null) mStartMarker.destroy();
        if (mEndMarker != null) mEndMarker.destroy();
        mStartLocation = null;
        mEndLocation = null;
        mContent.setText("");
    }

    private void showDetailTaskActivity() {
        Intent intent = new Intent(getActivity(), DetailTask.class);
        Bundle bundle = new Bundle();
        bundle.putString("TaskTitle", mTitle.getText().toString());
        bundle.putDouble("StartLatitude", mStartLocation.latitude);
        bundle.putDouble("StartLongitude", mStartLocation.longitude);
        if (mEndLocation != null) {
            bundle.putBoolean("EndStatus", true);
            bundle.putDouble("EndLatitude", mEndLocation.latitude);
            bundle.putDouble("EndLongitude", mEndLocation.longitude);
        } else {
            bundle.putBoolean("EndStatus", true);
        }
        bundle.putString("TaskContent", mContent.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                if (selectingMode == 1)
                    mStartText.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                else if (selectingMode == -1)
                    mEndText.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
            }
        }
        selectingMode = 0;
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
