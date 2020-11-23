package com.example.myrehabilitaion.ui.PersonalInfo;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.myrehabilitaion.Activity_Registeration;
import com.example.myrehabilitaion.Encrypt;
import com.example.myrehabilitaion.Frag_ColorfulCalendar;
import com.example.myrehabilitaion.GlobalVariable;
import com.example.myrehabilitaion.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.app.Activity.RESULT_OK;

public class PersonalInfoFragment extends Fragment {
 //---------------------SQL---------------------
    ResultSet resultSet01;

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
    TextView psinfo_phonenum;
    ImageButton psinfo_imgbtn;
    Dialog mdlg_changeinfo;
    Dialog mdlg_changepasswd;

    EditText minfoname_change;
    EditText minfoemail_change;
    EditText minfophonenum_change;

    EditText minfo_originpasswd;
    EditText minfo_newpasswd;
    EditText minfo_newpasswdagain;

    String info_name;
    String info_email;
    String info_phone;


    Statement statement = null;
    Statement statement_pic = null;
    Statement statement_info = null;
    Statement statement_passwd = null;
    Statement statement_chkemail =null;

    Button btn;

    GlobalVariable gv;
    String userid;
    byte[] bArray;
    byte[] byteArray;
    String encodedImage;


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
        PersonalInfoFragment.InnerPagerStateAdapter pagerAdapter = new PersonalInfoFragment.InnerPagerStateAdapter(getActivity().getSupportFragmentManager());

        ViewPager viewPager = root.findViewById(R.id.viewPager);

        viewPager.setAdapter(pagerAdapter);

//        TabLayout tableLayout = root.findViewById(R.id.tabLayout);
//        tableLayout.setupWithViewPager(viewPager);

//--------------------SQL--------------------
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        psinfo_name = root.findViewById(R.id.psinfo_name);
        psinfo_email = root.findViewById(R.id.psinfo_email);
        psinfo_phonenum = root.findViewById(R.id.info_phonenum);

        bigPic = root.findViewById(R.id.bigPic);
        registerForContextMenu(bigPic);



        byte[] imgByte = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection!=null){
            try {
                gv = (GlobalVariable)getActivity().getApplicationContext();

                userid = gv.getUserID();

                statement = connection.createStatement();
                resultSet01 = statement.executeQuery("SELECT username, email, phone,pic FROM dbo.registered WHERE user_id = " + String.valueOf(userid) +";");

                while (resultSet01.next()){
                    psinfo_name.setText(resultSet01.getString(1).toString().trim());
                    psinfo_email.setText(resultSet01.getString(2).toString().trim());
                    psinfo_phonenum.setText(resultSet01.getString(3).toString().trim());
                    info_name=resultSet01.getString(1).toString().trim();
                    info_email=resultSet01.getString(2).toString().trim();
                    info_phone=resultSet01.getString(3).toString().trim();

                    byte[] decodeString = Base64.decode(resultSet01.getString(4).toString().trim(), Base64.DEFAULT);
                    Bitmap decodebitmap = BitmapFactory.decodeByteArray(
                            decodeString, 0, decodeString.length);
                    bigPic.setImageBitmap(decodebitmap);
                }




            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast toast = Toast.makeText(getActivity(),"connection is null", Toast.LENGTH_SHORT);
            toast.show();
        }

        psinfo_imgbtn = root.findViewById(R.id.imgbtn_changeinfo);
        psinfo_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlg_changeinfo = new Dialog(v.getContext());
                mdlg_changeinfo.setContentView(R.layout.dlg_changeinfo);
                mdlg_changeinfo.setCancelable(true);
                mdlg_changeinfo.show();
                minfoname_change = mdlg_changeinfo.findViewById(R.id.edt_infoname);
                minfoemail_change = mdlg_changeinfo.findViewById(R.id.edt_infoemail);
                minfophonenum_change = mdlg_changeinfo.findViewById(R.id.edt_infophonenum);

                minfoname_change.setText(info_name);
                minfoemail_change.setText(info_email);
                minfophonenum_change.setText(info_phone);

                Button minfo_chginfo = mdlg_changeinfo.findViewById(R.id.btn_chginfo);
                minfo_chginfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chginfo_sync_todb chginfo_sync_todb = new chginfo_sync_todb();
                        chginfo_sync_todb.execute();

                    }
                });

                Button btn_changepasswd = mdlg_changeinfo.findViewById(R.id.btn_changepasswd);
                btn_changepasswd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdlg_changepasswd = new Dialog(v.getContext());
                        mdlg_changepasswd.setContentView(R.layout.dlg_changepasswd);
                        mdlg_changepasswd.setCancelable(true);
                        mdlg_changepasswd.show();

                        minfo_originpasswd = mdlg_changepasswd.findViewById(R.id.edt_oldpasswd);
                        minfo_newpasswd = mdlg_changepasswd.findViewById(R.id.edt_newpasswd);
                        minfo_newpasswdagain = mdlg_changepasswd.findViewById(R.id.edt_newpasswdagain);
                        Button mbtn_chgpasswd = mdlg_changepasswd.findViewById(R.id.btn_checkconfirm);
                        mbtn_chgpasswd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(Encrypt.SHA512(minfo_originpasswd.getText().toString().trim()).equals(gv.getUserPassword())){
                                    if(minfo_newpasswd.length() < 6){
                                        Toast.makeText(getContext(), "密碼太短", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(minfo_newpasswd.getText().toString().trim().equals("") || minfo_newpasswdagain.getText().toString().trim().equals("")){

                                            Toast.makeText(getContext(),"請輸入密碼或確認密碼",Toast.LENGTH_SHORT).show();
                                        }else {
                                            if(minfo_newpasswd.getText().toString().trim().equals(minfo_newpasswdagain.getText().toString().trim())){
                                                chgpasswd_sync_todb chgpasswd_sync_todb = new chgpasswd_sync_todb();
                                                chgpasswd_sync_todb.execute();

                                                Toast.makeText(getContext(),"密碼更改完成",Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(getContext(),"密碼輸入不一致",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }
                                }else{
                                    Toast.makeText(getContext(),"舊密碼輸入錯誤",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                });

            }
        });
 //--------------------SQL--------------------


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
                                Bitmap default_icon = BitmapFactory.decodeResource(getContext().getResources(),
                                        R.drawable.man);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                default_icon.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                                byteArray = stream.toByteArray();
                                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                bigpic_sync_todb bigpic_sync_todb = new bigpic_sync_todb();
                                bigpic_sync_todb.execute();

                                bigPic.setImageBitmap(default_icon);

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
                mOptions.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOptions);

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight()) {
//                    ScalePic(bitmap, mPhone.heightPixels);
//                    Bitmap bm = toRoundBitmap(bitmap);

                    bigPic.setImageBitmap(bitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    bigpic_sync_todb bigpic_sync_todb = new bigpic_sync_todb();
                    bigpic_sync_todb.execute();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else {
//                    ScalePic(bitmap, mPhone.widthPixels);
//                    Bitmap bm = toRoundBitmap(bitmap);
                    bigPic.setImageBitmap(bitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    bigpic_sync_todb bigpic_sync_todb = new bigpic_sync_todb();
                    bigpic_sync_todb.execute();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
            catch (FileNotFoundException e)
            {
            }
        }else if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK ) {

            Bundle extras = data.getExtras();
            Bitmap bp = (Bitmap)  extras.get("data");

//                Bitmap bm = toRoundBitmap(bp);
            bigPic.setImageBitmap(bp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            byteArray = stream.toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            bigpic_sync_todb bigpic_sync_todb = new bigpic_sync_todb();
            bigpic_sync_todb.execute();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void ScalePic(Bitmap bitmap,int phone)
//    {
//        //縮放比例預設為1
//        float mScale = 1;
//
//        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
//        if(bitmap.getWidth() > phone )
//        {
//            //判斷縮放比例
//            mScale = (float)phone/(float)bitmap.getWidth();
//
//            Matrix mMat = new Matrix() ;
//            mMat.setScale(mScale, mScale);
//
//            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
//                    0,
//                    0,
//                    bitmap.getWidth(),
//                    bitmap.getHeight(),
//                    mMat,
//                    false);
//            bigPic.setImageBitmap(mScaleBitmap);
//        }
//        else bigPic.setImageBitmap(bitmap);
//    }
    public class InnerPagerStateAdapter extends FragmentStatePagerAdapter {
        public InnerPagerStateAdapter(FragmentManager fm){
            super(fm);
        }


//        @Override
//        public CharSequence getPageTitle(int postion){
//            switch (postion){
//                case 0:
//                    return "看診日程紀錄";
//                default:
//                    return null;
//            }
//        }


        @Override
        public Fragment getItem(int position){

            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new Frag_ColorfulCalendar();

                    break;
            }

            return fragment;

        }


        @Override
        public int getCount(){
            return 1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }

    public class bigpic_sync_todb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected String doInBackground(String... strings) {

            if (connection!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            statement_pic = connection.createStatement();
                            statement_pic.executeQuery("UPDATE dbo.registered SET pic= '"+encodedImage+"' WHERE user_id="+Integer.valueOf(gv.getUserID())+";");

                        }catch (Exception e){

                            isSuccess = false;
                            z = e.getMessage();

                        }

                    }
                }).start();
            }
            else {
            }
            return z;
        }
    }

    public class chginfo_sync_todb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected String doInBackground(String... strings) {

            if (connection!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            statement_chkemail = connection.createStatement();
                            statement_info = connection.createStatement();
                            ResultSet rs = statement_chkemail.executeQuery("SELECT * FROM dbo.registered WHERE email = '" + minfoemail_change.getText().toString().trim() +  "';");
                            if(minfoemail_change.getText().toString().trim().equals("")||minfoname_change.getText().toString().trim().equals("")||minfophonenum_change.getText().toString().trim().equals("")){
                                Toast.makeText(getContext(), "請填入資料", Toast.LENGTH_SHORT).show();
                            }else{
                                if(minfoemail_change.getText().toString().trim().equals(gv.getUserEmail().toString().trim()) ){
                                    statement_info.executeQuery("UPDATE dbo.registered SET username='"+minfoname_change.getText()+"', email='"+minfoemail_change.getText()+"', phone='"+minfophonenum_change.getText()+"' WHERE user_id="+Integer.valueOf(gv.getUserID())+";");
                                }else{
                                    if (rs.next()) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "此Email已被註冊", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        minfoemail_change.setText("");
                                    }else{
                                        statement_info.executeQuery("UPDATE dbo.registered SET username='"+minfoname_change.getText()+"', email='"+minfoemail_change.getText()+"', phone='"+minfophonenum_change.getText()+"' WHERE user_id="+Integer.valueOf(gv.getUserID())+";");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(),"成功更改資料",Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }
                            }

                        }catch (Exception e){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"成功更改資料",Toast.LENGTH_SHORT).show();
                                }
                            });
                            isSuccess = false;
                            z = e.getMessage();

                        }

                    }
                }).start();
            }
            else {
            }
            return z;
        }
    }

    public class chgpasswd_sync_todb extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected String doInBackground(String... strings) {

            if (connection!=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            statement_passwd = connection.createStatement();
                            statement_passwd.executeQuery("UPDATE dbo.registered SET password='"+  Encrypt.SHA512(String.valueOf(minfo_newpasswd.getText()))+"' WHERE user_id="+Integer.valueOf(gv.getUserID())+";");

                        }catch (Exception e){

                            isSuccess = false;
                            z = e.getMessage();

                        }

                    }
                }).start();
            }
            else {
            }
            return z;
        }
    }


}