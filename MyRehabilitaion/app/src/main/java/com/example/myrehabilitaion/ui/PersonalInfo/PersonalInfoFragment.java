package com.example.myrehabilitaion.ui.PersonalInfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myrehabilitaion.Class_Login;
import com.example.myrehabilitaion.Class_Registeration;
import com.example.myrehabilitaion.GlobalVariable;
import com.example.myrehabilitaion.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.app.Activity.RESULT_OK;

public class PersonalInfoFragment extends Fragment {
 //---------------------SQL---------------------

    private static String ip = "140.131.114.241";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "109-rehabilitation";
    private static String username = "case210906";
    private static String password = "1@case206";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    TextView psinfo_name;
    TextView psinfo_email;
    Statement statement = null;

    GlobalVariable gv;
    String userid;

//---------------------SQL---------------------

    ImageView bigPic ;
    protected static final int MENU_BUTTON_1 = Menu.FIRST;
    protected static final int MENU_BUTTON_2 = Menu.FIRST + 1;
    protected static final int MENU_BUTTON_3 = Menu.FIRST + 2;

    private DisplayMetrics mPhone;
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;
    private  static final int REQUEST_IMAGE_CAPTURE = 101;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragement_personalinformationpage, container, false);


//--------------------SQL--------------------
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        psinfo_name = root.findViewById(R.id.psinfo_name);
        psinfo_email = root.findViewById(R.id.psinfo_email);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Toast toast = Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(),"FAILURE", Toast.LENGTH_SHORT);
            toast.show();

        }

        if (connection!=null){
            try {
                gv = (GlobalVariable)getActivity().getApplicationContext();

                userid = gv.getUserID();

                statement = connection.createStatement();
                ResultSet resultSet01 = statement.executeQuery("SELECT username FROM dbo.registered WHERE user_id = '" + userid +"';");
                while (resultSet01.next()){
                    psinfo_name.setText(resultSet01.getString(1).toString().trim());
                }
                ResultSet resultSet02 = statement.executeQuery("SELECT email FROM dbo.registered WHERE user_id = '" + userid + "';");
                while (resultSet02.next()){
                    psinfo_email.setText(resultSet02.getString(1).toString().trim());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast toast = Toast.makeText(getActivity(),"connection is null", Toast.LENGTH_SHORT);
            toast.show();
        }
 //--------------------SQL--------------------

        bigPic = root.findViewById(R.id.bigPic);
        registerForContextMenu(bigPic);

        //讀取手機解析度
        mPhone = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        return root;
    }


    public Bitmap toRoundBitmap(Bitmap bitmap) {
//圓形圖片寬高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//正方形的邊長
        int r = 0;
//取最短邊做邊長
        if(width > height) {
            r = height;
        } else {
            r = width;
        }
//構建一個bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
//new一個Canvas,在backgroundBmp上畫圖
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
//設定邊緣光滑,去掉鋸齒
        paint.setAntiAlias(true);
//寬高相等,即正方形
        RectF rect = new RectF(0, 0, r, r);
//通過制定的rect畫一個圓角矩形,當圓角X軸方向的半徑等於Y軸方向的半徑時,
//且都等於r/2時,畫出來的圓角矩形就是圓形
        canvas.drawRoundRect(rect, r/2, r/2, paint);
//設定當兩個圖形相交時的模式,SRC_IN為取SRC圖形相交的部分,多餘的將被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//canvas將bitmap畫在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
//返回已經繪畫好的backgroundBmp
        return backgroundBmp;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_BUTTON_1, 0, "從相簿取得大頭貼");
        menu.add(0, MENU_BUTTON_2, 0, "相機拍照");
        menu.add(0, MENU_BUTTON_3, 0, "刪除大頭貼");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case MENU_BUTTON_1:
                Toast.makeText(getActivity(), "您開啟了相簿", Toast.LENGTH_SHORT).show();

                Intent intent01 = new Intent();
                intent01.setType("image/*");
                intent01.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent01, PHOTO);

                break;
            case MENU_BUTTON_2:
                Toast.makeText(getContext(), "您開啟了相機", Toast.LENGTH_SHORT).show();

                //開啟相機功能，並將拍照後的圖片存入SD卡相片集內，須由startActivityForResult且帶入requestCode進行呼叫，原因為拍照完畢後返回程式後則呼叫onActivityResult
//                ContentValues value = new ContentValues();
//                value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//                Uri uri= getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        value);
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri.getPath());
//                startActivityForResult(intent, CAMERA);
                Intent imageTakeIntent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(imageTakeIntent.resolveActivity(getActivity().getPackageManager()) !=null){
                    startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
                }

                break;
            case MENU_BUTTON_3:

                final Snackbar snackbar = Snackbar.make(getView(),"確定刪除圖片?",Snackbar.LENGTH_SHORT)
                        .setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bigPic.setImageDrawable(null);
                                Toast.makeText(getActivity(), "您刪除了大頭貼", Toast.LENGTH_SHORT).show();
                            }
                        });
                snackbar.show();


                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ( requestCode == PHOTO  && data != null)
        {
            //取得照片路徑uri
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();

            try
            {
                //讀取照片，型態為Bitmap
                BitmapFactory.Options mOptions = new BitmapFactory.Options();
//Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
                mOptions.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOptions);

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight()) {
                    ScalePic(bitmap, mPhone.heightPixels);
                    Bitmap bm = toRoundBitmap(bitmap);
                    bigPic.setImageBitmap(bm);
                }
                else {
                    ScalePic(bitmap, mPhone.widthPixels);
                    Bitmap bm = toRoundBitmap(bitmap);
                    bigPic.setImageBitmap(bm);
                }
            }
            catch (FileNotFoundException e)
            {
            }
        }else if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK ) {

                Bundle extras = data.getExtras();
                Bitmap bp = (Bitmap)  extras.get("data");
                Bitmap bm = toRoundBitmap(bp);
                bigPic.setImageBitmap(bm);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap,int phone)
    {
        //縮放比例預設為1
        float mScale = 1;

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if(bitmap.getWidth() > phone )
        {
            //判斷縮放比例
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix() ;
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    mMat,
                    false);
            bigPic.setImageBitmap(mScaleBitmap);
        }
        else bigPic.setImageBitmap(bitmap);
    }


}