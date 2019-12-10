package com.example.wine;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.util.SparseArray;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wine.Bean.WineBean;
import com.example.wine.Util.DatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.RadioGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;
    private ArrayList<WineBean> wineBeans=new ArrayList<WineBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //将每个菜单ID作为一组ID传递，因为每个菜单都应该被视为顶级目的地。
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main,
                R.id.nav_search, R.id.nav_favorites, R.id.nav_share,
                R.id.nav_history, R.id.nav_version, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //右侧菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //弹出抽屉
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /*后退键*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        HeadlineFragment headlineFragment=HeadlineFragment.newInstance("头条");
        KnowFragment knowFragment=KnowFragment.newInstance("知道");
        ConsultFragment consultFragment= ConsultFragment.newInstance("咨询");
        ManageFragment manageFragment=ManageFragment.newInstance("经营");
        DataFragment dataFragment=DataFragment.newInstance("数据");
        mFragmentSparseArray.append(R.id.headline_tab, headlineFragment);
        mFragmentSparseArray.append(R.id.know_tab, knowFragment);
        mFragmentSparseArray.append(R.id.consult_tab, consultFragment);
        mFragmentSparseArray.append(R.id.manage_tab, manageFragment);
        mFragmentSparseArray.append(R.id.data_tab, dataFragment);
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });
        // 默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.headline_tab)).commit();
        //爬取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="http://list.yesmywine.com/";
                    Document doc= Jsoup.connect(url).get();
                    Elements goods=doc.select("[data-dts=\"L201\"]");
                    ArrayList<String> hrefs=new ArrayList<String>();

                    for(int i=0;i<goods.size();i++)
                    {
                        WineBean wineBean=new WineBean();
                        String href=goods.get(i).select("a").attr("href");
                        href="http:"+href;
                        hrefs.add(href);
                        String price=goods.get(i).select("p.price").text();
                        String imgurl=goods.get(i).select("img").attr("original");
                        wineBean.setImgurl(imgurl);
                        wineBean.setPrice(price);
                        wineBeans.add(wineBean);

                    }
                    for (int i=0;i<hrefs.size();i++)
                    {
                        Document document=Jsoup.connect(hrefs.get(i)).get();
                        String id=document.select("div.temperature.clearfix").select("span").text();
                        wineBeans.get(i).setId(id);
                        Elements temp=document.select("div.xiangqing");
                        String type=temp.select("span.zonglei").attr("title");
                        String infor=temp.select("span").text();
                        wineBeans.get(i).setType(type);
                        wineBeans.get(i).setInfor(infor);
                        Elements temp1=document.select("div.crumb");
                        String name=temp1.select("strong").text();
                        wineBeans.get(i).setName(name);

                    }
                    for (int i=0;i<wineBeans.size();i++)
                    {
                        WineBean wine =wineBeans.get(i);
                        Log.e("wine",wineBeans.get(i).toString());
                        //依靠DatabaseHelper带全部参数的构造函数创建数据库
                        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "test.db",null,1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Log.e("db",db.getPath());
                        ContentValues values = new ContentValues();
                        values.put("id",wine.getId());
                        values.put("name",wine.getName());
                        values.put("type",wine.getType());
                        values.put("price",wine.getPrice());
                        values.put("infor",wine.getInfor());
                        values.put("imgurl",wine.getImgurl());
                        db.insert("wines",null,values);
                    }


                }catch (Exception e){
                    Log.d("main","error...");
                }
            }

        }).start();


    }


}
