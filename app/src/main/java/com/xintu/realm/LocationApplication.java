package com.xintu.realm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

/**
 * Created in Feb 8, 2017 12:59:13 PM
 *
 * @author fanjf
 */
public class LocationApplication extends MultiDexApplication {

    private static LocationApplication mLocationApplication;

    public static LocationApplication getInstance () {
        return mLocationApplication;
    }

    @Override
    public void onCreate () {
        super.onCreate();
        mLocationApplication = this;
        Realm.init( this );
        //可以在此进行配置
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name( "study.realm" )
//                .schemaVersion( 0 )
//                .build();
//        Realm.setDefaultConfiguration( config );


    }

}
