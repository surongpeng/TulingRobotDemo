package com.example.sure.tulingdemo9;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements httpGetDataListener,View.OnClickListener{


    private HttpData httpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText text;
    private Button btn;
    private String content_str;
    private TextAdapter adapter;
    private String[] welcome_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        lv = (ListView) findViewById(R.id.lv);
        text = (EditText) findViewById(R.id.sendText);
        btn = (Button) findViewById(R.id.send_btn);
        lists = new ArrayList<ListData>();
        btn.setOnClickListener(this);
        adapter = new TextAdapter(lists,this);
        lv.setAdapter(adapter);
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(),ListData.RECIVER);
        lists.add(listData);
    }


    private String getRandomWelcomeTips(){
        String welcome_tip = null;
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random()*(welcome_array.length - 1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }

    @Override
    public void getDataUrl(String data) {
        //System.out.println(data);
        parseText(data);
    }

    public void parseText(String str){

        try {
            JSONObject jb = new JSONObject(str);
//            System.out.println(jb.getString("code"));
//            System.out.println(jb.getString("text"));
            ListData listData;
            listData = new ListData(jb.getString("text"),ListData.RECIVER);
            lists.add(listData);
            remove(lists);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        content_str = text.getText().toString();
        text.setText("");
        //去掉空格
        String dropk = content_str.replace(" ","");
        String droph = dropk.replace("\n","");
        ListData listData;
        listData = new ListData(content_str,ListData.SEND);
        lists.add(listData);
        remove(lists);
        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=418e3d115b9e5767b178021a95bc8dd7&info="
                +droph,
                this).execute();
    }

    public void remove(List lists){
        if(lists.size()>30){
            for (int i = 0; i<lists.size()-30;i++){
                lists.remove(i);
            }
        }
    }
}
