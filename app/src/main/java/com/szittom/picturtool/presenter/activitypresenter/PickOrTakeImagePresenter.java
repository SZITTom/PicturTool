package com.szittom.picturtool.presenter.activitypresenter;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.jude.beam.Utils;
import com.jude.beam.expansion.BeamBasePresenter;
import com.szittom.picturtool.interfaces.CameraClick;
import com.szittom.picturtool.interfaces.PickListClick;
import com.szittom.picturtool.model.PickOrTakeModel;
import com.szittom.picturtool.model.bean.SingleImageDirectories;
import com.szittom.picturtool.model.bean.SingleImageModel;
import com.szittom.picturtool.utils.AnimationUtils;
import com.szittom.picturtool.utils.CommonUtils;
import com.szittom.picturtool.utils.T;
import com.szittom.picturtool.view.activity.PickBigImagesActivity;
import com.szittom.picturtool.view.activity.PickOrTakeImageActivity;
import com.szittom.picturtool.view.adapter.PickOrTakeAdapter;
import com.szittom.picturtool.view.adapter.PickOrTakeListAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SZITTom on 2016/5/12.
 */
public class PickOrTakeImagePresenter extends BeamBasePresenter<PickOrTakeImageActivity> implements View.OnTouchListener, AbsListView.OnScrollListener {

    /**
     * 按时间排序的所有图片list
     */
    private ArrayList<SingleImageModel> allImages;
    /**
     * 按目录排序的所有图片list
     */
    private ArrayList<SingleImageDirectories> imageDirectories;
    private PickOrTakeAdapter mAdapter;
    private PickOrTakeListAdapter mLAdapter;

    /**
     * 时间显示渐隐动画
     */
    private AlphaAnimation fadeAnimation;
    /**
     * 列表上拉动画
     */
    private ObjectAnimator animation;
    /**
     * 列表下拉动画
     */
    private ObjectAnimator reverseanimation;

    private int firstVisibleItem = 0;
    private int currentState = SCROLL_STATE_IDLE;
    private int currentTouchState = MotionEvent.ACTION_UP;
    /**
     * 最新一张图片的时间
     */
    private long lastPicTime = 0;

    public static final int CODE_FOR_TAKE_PIC = 3;
    public static final int CODE_FOR_WRITE_PERMISSION = 100;
    public static final int CODE_FOR_BIG_PIC = 43;

    /**
     * 相机图片保存的路径
     */
    private String tempPath;

    @Override
    protected void onCreateView(@NonNull PickOrTakeImageActivity view) {
        super.onCreateView(view);

        getView().getGridView().setOnTouchListener(this);
        startGetImageThread();
        initAnimation();
    }

    private void startGetImageThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                allImages = PickOrTakeModel.getInstance().startGetImageThread(getView());

                getView().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //GirdiView 网格内容设置
                        mAdapter = new PickOrTakeAdapter(getView(), allImages, true);
                        getView().getGridView().setAdapter(mAdapter);
                        getView().getGridView().setOnScrollListener(PickOrTakeImagePresenter.this);
                        mAdapter.setmClick(new CameraClick() {
                            @Override
                            public void onClick(SingleImageModel singleImageModel, int position) {
                                if (position == 0 && singleImageModel.path == null) {//去相机
                                    takePic();
                                } else {//跳转到查看相册列表
                                    jumpPickBigImages(position, mAdapter.getData());
                                }
                            }

                            @Override
                            public void onChoosePick(ArrayList<SingleImageModel> singleImageModelList) {
                                //更新页面
                                getView().setTextViewData(singleImageModelList);

                            }
                        });
                        //ListView 列表内容设置
                        mLAdapter = new PickOrTakeListAdapter(getView(), null);
                        getView().getLv_directories().setAdapter(mLAdapter);
                        mLAdapter.setmClick(new PickListClick() {
                            @Override
                            public void onItemClick(ArrayList<SingleImageModel> singleImageModelList, int position) {
                                if (position == 0){//全部图片
                                    mAdapter.setData(singleImageModelList,true);
                                    getView().showChooseImageDir("全部图片");
                                }else {
                                    mAdapter.setData(singleImageModelList,false);
                                    getView().showChooseImageDir(new File(imageDirectories.get(position).directoryPath).getName());
                                }
                                getView().onChoose_image();
                            }
                        });

                    }
                });
            }
        };

        new Thread(runnable).start();

    }

    /**
     * 跳转到PickBig页面
     *
     * @param position
     *                  选中照片的位置
     * @param modelList
     *                  需要显示的列表
     */
    public void jumpPickBigImages(int position, ArrayList<SingleImageModel> modelList) {
        Intent intent = new Intent(getView(), PickBigImagesActivity.class);
        intent.putParcelableArrayListExtra(PickBigImagesActivity.EXTRA_DATA, modelList);
        intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC,position);
        getView().startActivityForResult(intent, CODE_FOR_BIG_PIC);
    }

    private void initAnimation() {
        fadeAnimation = AnimationUtils.getFadeAnimation();
        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getView().getmRl_date().setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        final int hight = Utils.dip2px(getView(), 400);
        animation = AnimationUtils.getUpAnimation(getView().getLv_directories(),hight);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getView().getLv_directories().getLayoutParams();
                getView().getRl_choose_directory().setAlpha(1 - Math.abs(value / hight));
                params.bottomMargin = value;
                getView().getLv_directories().setLayoutParams(params);
                getView().getLv_directories().invalidate();
                getView().getRl_choose_directory().invalidate();
            }
        });

        reverseanimation = AnimationUtils.getDownAnimation(getView().getLv_directories(),hight);
        reverseanimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getView().getLv_directories().getLayoutParams();
                params.bottomMargin = value;
                getView().getLv_directories().setLayoutParams(params);
                getView().getRl_choose_directory().setAlpha(1 - Math.abs(value / hight));
                if (value <= -Utils.dip2px(getView(), 300)) {
                    getView().getRl_choose_directory().setVisibility(View.GONE);
                }
                getView().getLv_directories().invalidate();
                getView().getRl_choose_directory().invalidate();
            }
        });

    }

    /**
     * 6.0版本之后需要动态申请权限
     */
    private void getAllImages(){
        //使用兼容库就无需判断系统版本
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getView().getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            startGetImageThread();
        }
        //需要弹出dialog让用户手动赋予权限
        else{
            ActivityCompat.requestPermissions(getView(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }
    }

    /**
     * 显示文件夹列表
     */
    public void onChoose_Image() {
        if (getView().getRl_choose_directory().getVisibility() == View.VISIBLE) {
            reverseanimation.start();
        } else {
            UpdateListData();
            getView().getRl_choose_directory().setVisibility(View.VISIBLE);
            animation.start();
        }
        getView().setTextViewData(mAdapter.getCheckedDatas());
    }

    public void UpdateListData(){
        imageDirectories = PickOrTakeModel.getInstance().startSingleDir(allImages);
        mLAdapter.setData(imageDirectories);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentState = scrollState;
        if (currentState == SCROLL_STATE_IDLE) {
            getView().getmRl_date().setAnimation(fadeAnimation);
            fadeAnimation.startNow();
        }
        if (currentTouchState == MotionEvent.ACTION_UP && currentState != SCROLL_STATE_FLING) {
            getView().getmRl_date().setAnimation(fadeAnimation);
            fadeAnimation.startNow();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        //保证当选择全部文件夹的时候，显示的时间为第一个图片，排除第一个拍照图片
        if (firstVisibleItem > 0) {
            firstVisibleItem--;
        }

        if (lastPicTime != getImageDirectoryModelDateFromMapById(firstVisibleItem)) {
            lastPicTime = getImageDirectoryModelDateFromMapById(firstVisibleItem);
        }

        String time = PickOrTakeModel.getInstance().calculateShowTime(lastPicTime * 1000);
        if (currentState == SCROLL_STATE_TOUCH_SCROLL) {
            getView().showTimeLine(time);
            fadeAnimation.cancel();
        }
        if (currentTouchState == MotionEvent.ACTION_UP && currentState == SCROLL_STATE_FLING) {
            getView().showTimeLine(time);
            fadeAnimation.cancel();
        }
    }

    /**
     * 根据id获取map中相对应的图片时间
     */
    private long getImageDirectoryModelDateFromMapById(int position) {
        if (allImages.size() == 0) {
            return System.currentTimeMillis();
        }
        if (position == 0) {
            position++;
        }

        //如果是选择的全部图片
//        if(currentShowPosition == -1){
        return allImages.get(position).date;
//        }else{
//            return imageDirectories.get(currentShowPosition).images.getImages().get(position).date;
//        }
    }

    /**
     * 调用系统相机进行拍照
     */
    private void takePic() {
        String name = "temp";
        File filePath = new File(CommonUtils.getDataPath());
        if (!filePath.exists()){
            filePath.mkdirs();
        }
        tempPath = CommonUtils.getDataPath() + name + ".jpg";
        File file = new File(tempPath);
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        getView().startActivityForResult(intent, CODE_FOR_TAKE_PIC);
    }


    public void onActivittResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getView().RESULT_OK) return;
        switch (requestCode) {
            case CODE_FOR_TAKE_PIC:
                //临时文件的文件名

                T.showToast("拍照的图片 "+tempPath);

                //扫描最新的图片进相册
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File(tempPath));
                intent.setData(uri);
                getView().sendBroadcast(intent);

                //重新拉取最新数据库文件
                getAllImages();
                break;
            case CODE_FOR_BIG_PIC:
                //选中列表 listDatas , mAdapter.getData() 当前列表, allimages 全部列表
                ArrayList<SingleImageModel> listDatas = data.getParcelableArrayListExtra(PickBigImagesActivity.EXTRA_ALL_PICK_DATA);
                if (Utils.isArrayisEmpty(listDatas))  return;

                if (allImages.size() == listDatas.size()){
                    Collections.copy(allImages,listDatas);
                }else {
                    for (SingleImageModel singleImageModel : listDatas){
                        for (SingleImageModel singleImageModel1 : mAdapter.getData()){
                            if (singleImageModel.id == singleImageModel1.id){
                                singleImageModel1.isPicked = singleImageModel.isPicked;
                            }
                        }
                        for (SingleImageModel singleImageModel1 : allImages){
                            if (singleImageModel.id == singleImageModel1.id){
                                singleImageModel1.isPicked = singleImageModel.isPicked;
                            }
                        }
                    }
//                    UpdateListData();
                }
                getView().setTextViewData(mAdapter.getCheckedDatas());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    public PickOrTakeAdapter getAdapter() {
        return mAdapter;
    }

}
