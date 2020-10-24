package com.example.myrehabilitaion;

import android.app.Application;

public class GlobalVariable extends Application {
    private String Email;     //User 名稱
    private String Password;         //User 年紀
    private String ServiceName;
    private String CaseName;

    //修改 變數値
    public void setUserEmail(String email){
        this.Email = email;
    }
    public void setUserPassword(String passwd){
        this.Password = passwd;
    }
    public void setServiceName(String servicename){ this.ServiceName = servicename; }
    public void setCaseName(String casename){ this.CaseName = casename; }

    //取得 變數值
    public String getUserEmail() {
        return Email;
    }
    public String getUserPassword(){
        return Password;
    }
    public String getServiceName(){return ServiceName; }
    public String getCaseName(){return CaseName; }

}