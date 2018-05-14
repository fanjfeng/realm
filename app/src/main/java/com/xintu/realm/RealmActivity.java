package com.xintu.realm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xintu.realm.adapter.CommonAdapter;
import com.xintu.realm.adapter.ViewHolder;
import com.xintu.realm.entity.CmlUser;
import com.xintu.realm.entity.Title;
import com.xintu.realm.util.DBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Realm
 * Created by fanjianfeng on 2018/5/11.
 */
public class RealmActivity extends AppCompatActivity {

    private List< Title > mData = new ArrayList<>();

    private CommonAdapter< Title > mAdapter;

    private RecyclerView mRecyclerView;

    private TextView content;

    private DBUtil mDBUtil;

    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_realm );
        mRecyclerView = findViewById( R.id.recycle_view );
        content = findViewById( R.id.content );

        mDBUtil = DBUtil.getInstance();

        Title title = new Title();
        title.setName( "创建Realm表" );
        mData.add( title );
        title = new Title();
        title.setName( "新增一条Realm" );
        mData.add( title );
        title = new Title();
        title.setName( "更新一条Realm" );
        mData.add( title );
        title = new Title();
        title.setName( "删除一条Realm" );
        mData.add( title );
        title = new Title();
        title.setName( "删除全部数据" );
        mData.add( title );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        mAdapter = new CommonAdapter< Title >( this, mData, R.layout.item_sq_lite ) {
            @Override
            public void convert ( ViewHolder helper, Title item ) {
                helper.setText( R.id.title, item.getName() );
            }
        };

        mRecyclerView.setAdapter( mAdapter );
        mAdapter.setOnItemClickLitener( new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener ( View view, int position ) {
                CmlUser mCmlUser;
                switch ( position ) {
                    case 0:
                        mCmlUser = mDBUtil.getCmlUserFirst();
                        if ( null == mCmlUser ) {
                            content.setText( "没有数据" );
//                            finish();
                            return;
                        }
                        for ( CmlUser cmlUser : mDBUtil.getCmlUserAll() ) {
                            Log.e( "全部数据", cmlUser.toString() );
                        }
                        content.setText( mCmlUser.toString() );
//                        1.使用executeTransaction进行操作
//                        mRealm.executeTransaction( new Realm.Transaction() {
//                            @Override
//                            public void execute ( Realm realm ) {
//                                RealmResults< CmlUser > cl = mRealm.where( CmlUser.class ).findAll();
//                                for ( CmlUser cmlUser : mDBUtil.getCmlUserAll() ) {
//                                    Log.e( "全部数据", cmlUser.toString() );
//                                }
//                            }
//                        } );

//                        2.使用
//                         mRealm.beginTransaction();
//                         ...处理...
//                         mRealm.commitTransaction();
//                        mRealm.beginTransaction();
//                        RealmResults< CmlUser > cl = mRealm.where( CmlUser.class ).findAll();
//                        for ( CmlUser cmlUser : cl ) {
//                            Log.e( "全部数据", cmlUser.toString() );
//                        }
//                        mRealm.commitTransaction();
                        break;
                    case 1:
                        mCmlUser = new CmlUser();
                        mCmlUser.setId( 1 );
                        mCmlUser.setName( "测试1" );
                        mCmlUser.setDemo( "好好的测试" );
                        mDBUtil.saveUser( mCmlUser );

//                        mRealm.executeTransaction( new Realm.Transaction() {
//                            @Override
//                            public void execute ( Realm realm ) {
////                                有主键时使用
////                                realm.copyToRealmOrUpdate( mCmlUser );//java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.dianxun.study.realm.CmlUser
//                                realm.copyToRealm( mCmlUser );
//                            }
//                        } );
                        break;
                    case 2:
//                        mCmlUser = mDBUtil.getCmlUserFirst();
                        CmlUser cu = new CmlUser();
                        cu.setId( 1 );
                        cu.setName( "修改后测试2" );
                        cu.setDemo( "测试完成" );
                        mDBUtil.saveUser( cu );
//                        mRealm.executeTransaction( new Realm.Transaction() {
//                            @Override
//                            public void execute ( Realm realm ) {
//                                CmlUser mCmlUser = realm.where( CmlUser.class ).findFirst();
//                                mCmlUser.setName( "修改后测试2" );
//                                mCmlUser.setDemo( "测试完成" );
////                                realm.copyToRealmOrUpdate( mCmlUser );//有主键时使用
//                                realm.copyToRealm( mCmlUser );
//                            }
//                        } );
                        break;
                    case 3:
//                        mRealm.executeTransaction( new Realm.Transaction() {
//                            @Override
//                            public void execute ( Realm realm ) {
//                                CmlUser mCmlUser = realm.where( CmlUser.class ).findFirst();
//                                mCmlUser.deleteFromRealm();
//                            }
//                        } );
                        mDBUtil.delete();
                        break;
                    case 4:
//                        mRealm.executeTransaction( new Realm.Transaction() {
//                            @Override
//                            public void execute ( Realm realm ) {
//                                RealmResults< CmlUser > cl = mRealm.where( CmlUser.class ).findAll();
//                                cl.deleteAllFromRealm();
//                            }
//                        } );
                        mDBUtil.deleteAll();
                        break;
                }
            }
        } );
    }

    @Override
    protected void onDestroy () {
//        Log.e( "是否关闭", String.valueOf( mRealm.isClosed() ) );
//        if ( !mRealm.isClosed() ) {
//            mRealm.close();
//            Log.e( "关闭", "已关闭" );
//        }
        super.onDestroy();
    }
}
