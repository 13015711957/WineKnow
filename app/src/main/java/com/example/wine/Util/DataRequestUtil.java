package com.example.wine.Util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wine.ArticleDetailActivity;
import com.example.wine.Bean.NewBean;
import com.example.wine.Bean.WineBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DataRequestUtil {
    /**
     * 爬取酒的数据
     * @param context
     */
    public static void getWineData(Context context){
        ArrayList<WineBean> wineBeans=new ArrayList<WineBean>();
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
                //Log.e("wine",wineBeans.get(i).toString());
                //依靠DatabaseHelper带全部参数的构造函数创建数据库
                DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id",wine.getId());
                values.put("name",wine.getName());
                values.put("type",wine.getType());
                values.put("price",wine.getPrice());
                values.put("infor",wine.getInfor());
                values.put("imgurl",wine.getImgurl());
                db.insert("wines",null,values);
            }
        }
        catch (IOException e)
        {
            Log.e("winedata","error......");
        }
    }
    public static void getNewsData(Context context){
        ArrayList<NewBean> newBeans=new ArrayList<NewBean>();
        try {
            String home_url="http://www.jianiang.cn/hangye/";
            Document doc= Jsoup.connect(home_url).get();
            Elements ul=doc.select("ul.e2");
            Elements news=ul.select("li");
            for (int i=0;i<news.size();i++)
            {
                String title=news.get(i).select("a.title").text();
                String time=news.get(i).select("span.info").text();
                String content=news.get(i).select("p.intro").text();
                String imgurl=news.get(i).select("img").attr("src");
                String url=news.get(i).select("a.preview").attr("href");
                NewBean newBean=new NewBean(title,time,content,imgurl,url);
                newBeans.add(newBean);
                //Log.e("news",newBean.toString());
                DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("time",time);
                values.put("content",content);
                values.put("imgurl",imgurl);
                values.put("url",url);
                db.insert("News",null,values);

            }

        }catch (IOException e){
            Log.e("newsdata","error......");
        }

    }
}
