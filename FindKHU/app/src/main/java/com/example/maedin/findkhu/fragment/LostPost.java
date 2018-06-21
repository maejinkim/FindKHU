package com.example.maedin.findkhu.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.item.ImageItem;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.item.MemberInfoItem;
import com.example.maedin.findkhu.lib.BitmapLib;
import com.example.maedin.findkhu.lib.FileLib;
import com.example.maedin.findkhu.lib.RemoteLib;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostPost extends Fragment implements View.OnClickListener{

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    int mYear, mMonth, mDay;
    View view;
    Button btn_post;
    Button btn_pic;
    Button btn_map;
    Button btn_date;
    EditText edit_title;
    EditText edit_contents;
    TextView edit_date;
    Spinner category;
    ImageView image;

    Activity context;
    SpinnerAdapter sAdapter;

    File imageFile;
    ImageItem imageItem;
    String imageFilename;
    boolean isSavingImage = false;

    int cat_id;
    String date = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.lost_post, container, false);
        context = this.getActivity();

        btn_post = (Button) view.findViewById(R.id.btn_lost_post_ok);
        btn_pic = (Button) view.findViewById(R.id.btn_lost_pic);
        btn_map = (Button) view.findViewById(R.id.btn_lost_select_map);
        edit_title = (EditText) view.findViewById(R.id.edit_lost_title);
        edit_contents = (EditText) view.findViewById(R.id.edit_lost_contents);
        edit_date = (TextView) view.findViewById(R.id.edit_lost_date);
        edit_date.setText(date);
        btn_date = (Button) view.findViewById(R.id.btn_select_date);
        image = (ImageView) view.findViewById(R.id.img_pic);


        btn_post.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        btn_date.setOnClickListener(this);

        final Activity root = getActivity();
        category = (Spinner) view.findViewById(R.id.spinner_category); //butterknife 없을경우
        sAdapter = ArrayAdapter.createFromResource(root, R.array.category, android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(sAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_id = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat_id = 0;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageItem = new ImageItem();
        imageItem.item_id = ((MyApp)getActivity().getApplication()).getMemberID();
        imageItem.item_type = ((MyApp)getActivity().getApplication()).getPostSelect();

        //imageFilename = infoSeq + "_" + String.valueOf(System.currentTimeMillis());
        imageFile = FileLib.getInstance().getImageFile(context, imageFilename);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_lost_select_map:

                //((MyApp)getActivity().getApplication()).setPostSelect(1); -> 글쓰기 눌렀을때 설정할것.
                ((MyApp)getActivity().getApplication()).setLostPost(this);
                ((MainActivity)getActivity()).replaceFragment(new MapSelect());
            break;
            case R.id.btn_select_date:
                new DatePickerDialog(this.getActivity(), mDateSetListener, mYear, mMonth, mDay).show();
                break;

            case R.id.btn_lost_pic:
                showImageDialog(context);

//
                break;

            case R.id.btn_lost_post_ok:
                saveImage();
                postUpload();

                break;
        }

    }

    /**
     * 이미지를 어떤 방식으로 선택할지에 대해 다이얼로그를 보여준다.
     * @param context 컨텍스트 객체
     */
    public void showImageDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("이미지 등록")
                .setSingleChoiceItems(R.array.camera_album_category, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    getImageFromCamera();
                                } else {
                                    getImageFromAlbum();
                                }

                                dialog.dismiss();
                            }
                        }).show();
    }

    private void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        context.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범으로부터 이미지를 선택할 수 있는 액티비티를 시작한다.
     */
    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FROM_CAMERA) {
                Picasso.with(context).load(imageFile).into(image);

            } else if (requestCode == PICK_FROM_ALBUM && data != null) {
                Uri dataUri = data.getData();

                if (dataUri != null) {
                    Picasso.with(context).load(dataUri).into(image);

                    Picasso.with(context).load(dataUri).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            BitmapLib.getInstance().saveBitmapToFileThread(imageUploadHandler,
                                    imageFile, bitmap);
                            isSavingImage = true;
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                }
            }
        }
    }

    /**
     * 사용자가 선택한 이미지와 입력한 메모를 ImageItem 객체에 저장한다.
     */
    private  void setImageItem() {
        imageItem.pic_name = imageFilename + ".png";
    }

    Handler imageUploadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isSavingImage = false;
            setImageItem();
            Picasso.with(context).invalidate(IRemoteService.IMAGE_URL + imageItem.pic_name);
        }
    };

    Handler finishHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            context.finish();
        }
    };


    DatePickerDialog.OnDateSetListener mDateSetListener =

            new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

                    mYear = year;

                    mMonth = monthOfYear;

                    mDay = dayOfMonth;

                    //텍스트뷰의 값을 업데이트함

            UpdateNow();

                }

            };

    void UpdateNow(){
        edit_date.setText(String.format("%d년 %d월 %d일", mYear, mMonth+1, mDay));
        date = edit_date.getText().toString();
    }

    /**
     * 이미지를 서버에 업로드한다.
     */
    private void saveImage() {
        if (isSavingImage) {
            return;
        }

        if (imageFile.length() == 0) {
            return;
        }

        setImageItem();

        ((MyApp)getActivity().getApplication()).setPic_id(Integer.parseInt(RemoteLib.getInstance().uploadItemImage(imageItem.item_id,
                imageItem.item_type, imageFile, finishHandler)));
        isSavingImage = false;
    }

    private void postUpload()
    {

        InfoItem infoItem = new InfoItem();
        infoItem.item_type = ((MyApp)getActivity().getApplication()).getPostSelect();
        infoItem.cat_id = cat_id;
        infoItem.user_id = ((MyApp)getActivity().getApplication()).getMemberID();
        infoItem.item_title = edit_title.getText().toString();
        infoItem.item_content = edit_contents.getText().toString();
        infoItem.item_reg_date = edit_date.getText().toString();
        infoItem.loc_id = ((MyApp)getActivity().getApplication()).getLoc_id();
        infoItem.pic_id = ((MyApp)getActivity().getApplication()).getPic_id();


        // 변경 사항 있을 경우
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);
        Call<ResponseBody> call = remoteService.insertItemInfo(infoItem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String loc_id = null;
                    try {
                        loc_id = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Response 리턴값", loc_id);
                    Log.e("포스트 등록", "성공");


                    if (((MyApp)getActivity().getApplication()).getPostSelect() == 1)
                    {
                        ((MainActivity)getActivity()).replaceFragment(new LostBoard());
                    }
                    else
                    {
                        ((MainActivity)getActivity()).replaceFragment(new FindBoard());
                    }


                } else {
                    Log.e("포스트 등록", "오류");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("marker 등록", "서버 연결 실패");
            }
        });
    }

}
