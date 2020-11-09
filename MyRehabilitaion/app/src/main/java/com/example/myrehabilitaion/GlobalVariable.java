package com.example.myrehabilitaion;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;

public class GlobalVariable extends Application {
    private String Email;     //User 名稱
    private String Password;         //User 年紀
    private String ServiceName;
    private String CaseName;
    private String UserID;
    private String UserName;

    private String ServiceID;
    private String CaseID;
    private String Str_Count;
    private String ClickDate;
    private Fragment Context_NewFrag;

    private String DeviceAddress = null;
    private String DeviceName = null;


    @Override
    public void onCreate()
    {
        super.onCreate();

    }


    //修改 變數値
    public void setUserEmail(String email){
        this.Email = email;
    }
    public void setUserPassword(String passwd){
        this.Password = passwd;
    }
    public void setServiceName(String servicename){ this.ServiceName = servicename; }
    public void setUserID(String userid){ this.UserID = userid; }
    public void setUserName(String username){ this.UserName = username; }
    public void setServiceID(String serviceid){ this.ServiceID = serviceid; }
    public void setCaseID(String caseid){ this.CaseID = caseid; }
    public void setCaseName(String casename){ this.CaseID = casename; }
    public void setStr_Count(String count){ this.Str_Count = count; }
    public void setClickDate(String clickDate){ this.ClickDate = clickDate; }
    public void setContext_NewFrag(Fragment frag){ this.Context_NewFrag = frag;}

    public void setDeviceAddress(String deviceAddress){ this.DeviceAddress = deviceAddress; }
    public void setDeviceName(String deviceName){ this.DeviceName = deviceName; }

    //取得 變數值
    public String getUserEmail() {
        return Email;
    }
    public String getUserPassword(){
        return Password;
    }
    public String getServiceName(){return ServiceName; }
    public String getUserID() { return UserID; }
    public String getUserName() { return UserName; }
    public String getServiceID() {
        return ServiceID;
    }
    public String getCaseName(){return CaseName; }
    public String getCaseID() {
        return CaseID;
    }
    public String getStr_Count() {
        return Str_Count;
    }
    public String getClickDate() { return ClickDate; }
    public Fragment getNew_Frag() {
        return Context_NewFrag;
    }

    public String getDeviceAddress() {
        return DeviceAddress;
    }
    public String getDeviceName() {
        return DeviceName;
    }


}