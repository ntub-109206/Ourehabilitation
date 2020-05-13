package com.example.rehabilitation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {

    private static final String DB_FILE = "Users.db",
            DB_TABLE = "Users";

    private UsersDbOpenHelper mUsersDbOpenHelper;

    private EditText mEdtName,
            mEdtMail,
            mEdtPassword,
            mEdtPswdconfir,
            mEdtPhone,
            mEdtBitrhday,
            mEdtIdentity;
    private CheckBox mChkman,mChkwmn;

    ;

    private TextView mTxtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_registeration);

        mUsersDbOpenHelper =
                new UsersDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        SQLiteDatabase usersDb = mUsersDbOpenHelper.getWritableDatabase();

        // 檢查資料表是否已經存在
        Cursor cursor = usersDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                        DB_TABLE + "'", null);

        if (cursor != null) {
            if (cursor.getCount() == 0)    // 沒有資料表，需要建立一個資料表
                usersDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "phonenumber TEXT NOT NULL," +
                        "Email TEXT NOT NULL," +
                        "sex TEXT NOT NULL," +
                        "birthday TEXT NOT NULL);");
            cursor.close();
        }

        usersDb.close();

        mEdtName = findViewById(R.id.edtName);
        mEdtPassword = findViewById(R.id.edtPassword);
        mEdtPswdconfir = findViewById(R.id.edtPasswordConfirm);
        mEdtPhone = findViewById(R.id.edtPhone);
        mEdtMail = findViewById(R.id.edtEmail);
        mEdtBitrhday = findViewById(R.id.edtBirthday);

        mChkman = findViewById(R.id.chkman);
        mChkwmn = findViewById(R.id.chkwmn);


        Button btnRgstr = findViewById(R.id.mbtnRegistr);
        //Button btnQuery = findViewById(R.id.btnQuery);
        //Button btnList = findViewById(R.id.btnList);

        btnRgstr.setOnClickListener(btnAddOnClick);
        //btnQuery.setOnClickListener(btnQueryOnClick);
        //btnList.setOnClickListener(btnListOnClick);
    }

    private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase usersDb = mUsersDbOpenHelper.getWritableDatabase();


            String mChkgender = getString(R.string.your_gender);
            if(mChkman.isChecked())
                mChkgender = mChkman.getText().toString();
            else if (mChkwmn.isChecked())
                mChkgender = mChkwmn.getText().toString();
            else
                Toast.makeText(RegisterPage.this, "請選性別",Toast.LENGTH_LONG).show();



            ContentValues newRow = new ContentValues();
            newRow.put("name", mEdtName.getText().toString());
            newRow.put("password", mEdtPassword.getText().toString());
            newRow.put("address", mEdtPswdconfir.getText().toString());
            newRow.put("name", mEdtPhone.getText().toString());
            newRow.put("gender",mChkgender);
            newRow.put("mail", mEdtMail.getText().toString());
            newRow.put("address", mEdtBitrhday.getText().toString());






            usersDb.insert(DB_TABLE, null, newRow);

            usersDb.close();

        }
    };


    /*private View.OnClickListener btnQueryOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase friendDb = mUsersDbOpenHelper.getWritableDatabase();

            Cursor c = null;

            if (!mEdtName.getText().toString().equals("")) {
                c = friendDb.query(true, DB_TABLE, new String[]{"name", "sex",
                        "address"}, "name=" + "\"" + mEdtName.getText().toString()
                        + "\"", null, null, null, null, null);
            } else if (!mEdtSex.getText().toString().equals("")) {
                c = friendDb.query(true, DB_TABLE, new String[]{"name", "sex",
                        "address"}, "sex=" + "\"" + mEdtSex.getText().toString()
                        + "\"", null, null, null, null, null);
            } else if (!mEdtAddr.getText().toString().equals("")) {
                c = friendDb.query(true, DB_TABLE, new String[]{"name", "sex",
                        "address"}, "address=" + "\"" + mEdtAddr.getText().toString()
                        + "\"", null, null, null, null, null);
            }

            if (c == null)
                return;

            if (c.getCount() == 0) {
                mTxtList.setText("");
                Toast.makeText(MainActivity.this, "沒有這筆資料", Toast.LENGTH_LONG)
                        .show();
            } else {
                c.moveToFirst();
                mTxtList.setText(c.getString(0) + c.getString(1)  + c.getString(2));

                while (c.moveToNext())
                    mTxtList.append("\n" + c.getString(0) + c.getString(1) +
                            c.getString(2));
            }

            friendDb.close();
        }
    };*/

    private View.OnClickListener btnListOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase friendDb = mUsersDbOpenHelper.getWritableDatabase();

            Cursor c = friendDb.query(true, DB_TABLE, new String[]{"name", "sex",
                    "address"}, 	null, null, null, null, null, null);

            if (c == null)
                return;

            if (c.getCount() == 0) {
                mTxtList.setText("");
                Toast.makeText(RegisterPage.this, "沒有資料", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                c.moveToFirst();
                mTxtList.setText(c.getString(0) + c.getString(1)  + c.getString(2));

                while (c.moveToNext())
                    mTxtList.append("\n" + c.getString(0) + c.getString(1)  +
                            c.getString(2));
            }

            friendDb.close();
        }
    };
}