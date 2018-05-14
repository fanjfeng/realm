package com.xintu.realm.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.xintu.realm.entity.CmlUser;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * realm登录用户数据库操作
 * Created by fan on 2018/5/11.
 */
public class DBUtil {

    //数据库名称
    private static final String dbName = "study.realm";

    //当前数据库版本
    private static final int dbVersion = 1;

    private static DBUtil mDbUtil;

    private Realm mRealm;

    private DBUtil () {
        RealmMigration realmMigration = new RealmMigration() {
            @Override
            public void migrate ( @NonNull DynamicRealm realm, long oldVersion, long newVersion ) {
                if ( oldVersion < newVersion ) {
                    //更新数据库结构（比如增加字段）
                    updateFields( realm );
                }
            }
        };

        RealmConfiguration config = new RealmConfiguration.Builder()
                //设置数据库名称
                .name( dbName )
                //设置数据库版本
                .schemaVersion( dbVersion )
                //版本变化时（迁移操作），怎么处理。。。
                .migration( realmMigration )
                //版本冲突时自动删除原数据库
//                .deleteRealmIfMigrationNeeded()
                .build();
        try {
            //手动调用Realm.migrateRealm()，不手动调起此方法，使用时才会调起RealmMigration
            Realm.migrateRealm( config, realmMigration );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        mRealm = Realm.getInstance( config );
    }

    /**
     * 获取实例
     *
     * @return DBUtil实例
     */
    public static DBUtil getInstance () {
        if ( mDbUtil == null ) {
            mDbUtil = new DBUtil();
        }
        return mDbUtil;
    }

    @SuppressWarnings ( "ConstantConditions" )
    private void updateFields ( @NonNull DynamicRealm realm ) {
        Map< String, String > filedMap = new HashMap<>();
        //set集合存放的是realm数据库的字段集
        Set< String > s = realm.getSchema().get( CmlUser.class.getSimpleName() ).getFieldNames();
        for ( String param : s ) {
            Log.e( "数据库字段Set", param );
            filedMap.put( param, param );
        }
        //反射时，会反射出这两项
        filedMap.put( "serialVersionUID", "serialVersionUID" );
        filedMap.put( "$change", "$change" );

        RealmSchema schema = realm.getSchema();
        RealmObjectSchema personSchema = schema.get( CmlUser.class.getSimpleName() );
        if ( personSchema != null ) {
            //反射，是获取实体类的所有私有属性（即我们更改表结构后的所有字段名）
            Field[] changeFields = CmlUser.class.getDeclaredFields();
            if ( filedMap.size() > changeFields.length ) {
                //减字段
                Map< String, String > changeFieldMap = new HashMap<>();
                for ( Field changeField : changeFields ) {
                    String changeName = changeField.getName();
                    //得到更改后表的字段
                    changeFieldMap.put( changeName, changeName );
                }
                for ( String field : filedMap.keySet() ) {
                    if ( changeFieldMap.containsKey( field ) ) {
                        Log.e( "数据库字段", field + "该字段还存在于表中" );
                    } else {
                        Log.e( "数据库字段", field + "该字段已移除" );
                        personSchema.removeField( field );
                    }
                }
            } else {
                //加字段
                for ( Field changeField : changeFields ) {
                    String changeName = changeField.getName();
                    if ( filedMap.containsKey( changeName ) ) {
                        //假如字段名已存在就进行下次循环
                        Log.e( "数据库字段", changeName + "该字段已存在" );
                    } else {
                        //不存在，就放到map中，并且给表添加字段
                        filedMap.put( changeName, changeName );
                        Log.e( "数据库字段", changeName + "该字段已添加到表中" );
                        //根据属性的类型给表增加字段
                        String type = changeField.getType().toString();
                        if ( type.equals( "class java.lang.String" ) ) {
                            //使用FieldAttribute.REQUIRED，必须再entity此字段加注解@Required
                            personSchema.addField( changeName, String.class );
//                            personSchema.addField( changeName, String.class, FieldAttribute.REQUIRED );
                            continue;
                        }
                        if ( type.equals( "int" ) ) {
                            //两种方式实现一致，并且不用加注解@Required
//                            personSchema.addField( changeName, int.class );
                            personSchema.addField( changeName, int.class, FieldAttribute.REQUIRED );
                            continue;
                        }
                        if ( type.equals( "class java.lang.Long" ) ) {
                            personSchema.addField( changeName, long.class, FieldAttribute.REQUIRED );
                            //Long修饰词，不能使用此方法，会造成io.realm.exceptions.RealmMigrationNeededException错误
                            // Native Method提示出错
                            //personSchema.addField( changeName, long.class );
                            continue;
                        }
                        if ( type.equals( "boolean" ) ) {
                            personSchema.addField( changeName, Boolean.class, FieldAttribute.REQUIRED );
                            //Boolean修饰词，不能使用此方法，会造成io.realm.exceptions.RealmMigrationNeededException错误
                            // Native Method提示出错
//                            personSchema.addField( changeName, Boolean.class );
                        } else {
                            Log.i( "数据库增加字段失败", "数据库出错" );
                        }
                    }
                }
            }
        } else {
            Log.e( "数据库无法更新", "personSchema为空，可能是没有实体类存在" );
        }
    }

    /**
     * 获取手机存储用户信息
     *
     * @param account 手机号码、name
     * @return 根据条件，查找到的第一个数据
     */
    private CmlUser getCmlUser ( String account ) {
        String columnName = "name";
        if ( StringUtil.isMobileNumber( account ) ) {
            columnName = "tel";
        }
        mRealm.beginTransaction();
        CmlUser mCmlUser = mRealm.where( CmlUser.class ).equalTo( columnName, account ).findFirst();
        mRealm.commitTransaction();
        return mCmlUser;
    }

    /**
     * 获取手机存储用户信息
     *
     * @return 第一个
     */
    public CmlUser getCmlUserFirst () {
        mRealm.beginTransaction();
        CmlUser mCmlUser = mRealm.where( CmlUser.class ).findFirst();
        mRealm.commitTransaction();
        if ( null == mCmlUser ) {
            return null;
        }
        return mCmlUser;
    }

    /**
     * 查询全部存储的数据
     */
    public RealmResults< CmlUser > getCmlUserAll () {
        mRealm.beginTransaction();
        RealmResults< CmlUser > mCmlUser = mRealm.where( CmlUser.class ).findAll();
        mRealm.commitTransaction();
        if ( StringUtil.isNullOrEmpty( mCmlUser ) && mCmlUser.size() == 0 ) {
            return null;
        }
        return mCmlUser;
    }


    /**
     * 保存用户信息
     *
     * @param mCmlUser 实体类
     */
    public void saveUser ( CmlUser mCmlUser ) {
        if ( getCmlUser( mCmlUser.getName() ) != null ) {
            mRealm.beginTransaction();
            mRealm.insertOrUpdate( mCmlUser );
            mRealm.commitTransaction();
            return;
        }
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate( mCmlUser );
        mRealm.commitTransaction();
    }

    /**
     * 删除数据--用户信息
     */
    public void delete () {
        mRealm.beginTransaction();
        CmlUser mCmlUser = mRealm.where( CmlUser.class ).findFirst();
        if ( mCmlUser == null ) {
            mRealm.commitTransaction();
            return;
        }
        mCmlUser.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 删除数据--用户信息
     */
    public void deleteAll () {
        mRealm.beginTransaction();
        RealmResults< CmlUser > mCmlUser = mRealm.where( CmlUser.class ).findAll();
        mCmlUser.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

}
