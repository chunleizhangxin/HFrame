package com.android.styy.entity;

public class NetWorkTypeEntity {

//    private NetWorkState state;

//    public NetWorkTypeEntity(NetWorkState state){
//        this.state = state;
//    }

    public NetWorkTypeEntity(){

    }

//    public NetWorkState getState() {
//        return state;
//    }
//
//    public void setState(NetWorkState state) {
//        this.state = state;
//    }

    public enum NetWorkState{

        WIFI,

        MOBILE,

        NON;

    }

}
