package com.example.foundations;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ProfileFragment extends DialogFragment{
    TextView fName,lName,lnumber, companyname, Email, phone;
    Button btn_confirm, btn_edit;
    ImageView profile_pic;
    Uri contentUri;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        AppActivity activity =(AppActivity) getActivity();
        String profile_pic_path = "/sdcard/DCIM/Camera/" + (activity.email)+ ".jpg";
        System.out.println(profile_pic_path);

        File file = new File(profile_pic_path);

        contentUri = Uri.fromFile(file);
        Bitmap bitmap = BitmapFactory.decodeFile(contentUri.getPath());

        fName = (TextView)view.findViewById(R.id.firstnameFrag);
        lName = (TextView)view.findViewById(R.id.lastnameFrag);
        lnumber = (TextView)view.findViewById(R.id.licenseNumberFrag);
        companyname = (TextView)view.findViewById(R.id.companyFrag);
        Email = (TextView)view.findViewById(R.id.emailFrag);
        phone = (TextView)view.findViewById(R.id.phoneFrag);
        btn_confirm =(Button)view.findViewById(R.id.confirm);
        btn_edit = (Button)view.findViewById(R.id.Profile_edit);

        profile_pic =(ImageView)view.findViewById(R.id.profile_pic);
        float degree = getDegree();

        Bitmap bitmap2 = rotateBitmap(bitmap, degree);

        //profile_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_account_circle_24));
        profile_pic.setImageBitmap(bitmap2);

        fName.setText(activity.fName);
        lName.setText(activity.lName);
        lnumber.setText(activity.License);
        companyname.setText(activity.Company);
        Email.setText(activity.email);
        phone.setText(activity.phone);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });



        return view;
    }
    public Bitmap rotateBitmap(Bitmap bitmap, float degree){
        try{
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();

            matrix.postRotate(degree);
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0,width, height, matrix, true);

            bitmap.recycle();
            return resizeBitmap;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
        double ratio = (double) targetWidth / (double) source.getWidth();
        int targetHeight = (int) (source.getHeight() * ratio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);

        if (result != source) {
            source.recycle();
        }
        return result;
    }



    public float getDegree(){
        try{
            ExifInterface exif = new ExifInterface(contentUri.getPath());
            int degree = 0;

            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (ori){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;

            }
            return (float)degree;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Fragmentstyle);
    }
}