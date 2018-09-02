package com.example.karthik.messagenwishes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karthik.messagenwishes.Data.Eventhelper;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String date="1";
    Eventhelper myevent=new Eventhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mn=(TextView)findViewById(R.id.mname);
        Button addingevent=(Button)findViewById(R.id.adde);
        Button deleteall=(Button)findViewById(R.id.dele);
        Button birthday=(Button)findViewById(R.id.hbd);
        Button congratulation=(Button)findViewById(R.id.congo);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,selectperson.class);
                i.putExtra("bid","1");
                startActivity(i);
            }
        });
        congratulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,selectperson.class);
                i.putExtra("bid","2");
                startActivity(i);
            }
        });
        ////
        int j;

        int month=Calendar.MONTH;
        mn.setText(monthname(month));
        int i=numdays(month);
        ListView l=(ListView)findViewById(R.id.dates);
        final ArrayList<String>days=new ArrayList<>();
        for(j=1;j<=i;j++){
            days.add(Integer.toString(j));
        }
        /////
        ArrayAdapter<String> monthadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
        l.setAdapter(monthadapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               date=days.get(i);
            }
        });
        Button showe=(Button)findViewById(R.id.refresh);
        showe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView elist=(ListView)findViewById(R.id.listofevents);
                ArrayList<String>listofev=myevent.eventtlist(date);
                ArrayAdapter<String>dayev=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,listofev);
                elist.setAdapter(dayev);

            }
        });
        addingevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,getevent.class);
                startActivityForResult(i,1);
            }
        });
        Button deletion=(Button)findViewById(R.id.dele);
        deletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myevent.deleteevents(date);
            }
        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==Activity.RESULT_OK){
                String mainevent;
                mainevent=data.getStringExtra("THE_EVENT");
                myevent.addevent(mainevent,date);
            }
        }
    }

    public String monthname(int month){
        String days="Jan";
        switch (month){
            case 0:days="Jan";
                break;
            case 1:days="Feb";
                break;
            case 2:days="Mar";
                break;
            case 3:days="Apr";
                break;
            case 4:days="May";
                break;
            case 5:days="Jun";
                break;
            case 6:days="Jul";
                break;
            case 7:days="Aug";
                break;
            case 8:days="Sep";
                break;
            case 9:days="Oct";
                break;
            case 10:days="Nov";
                break;
            case 11:days="Dec";
                break;
        }
        return days;
    }

    public int numdays(int month){
        int days=31;
        switch (month){
            case 0:days=31;
                   break;
            case 1:days=28;
                   break;
            case 2:days=31;
                   break;
            case 3:days=30;
                   break;
            case 4:days=31;
                   break;
            case 5:days=30;
                   break;
            case 6:days=31;
                   break;
            case 7:days=31;
                   break;
            case 8:days=30;
                   break;
            case 9:days=31;
                   break;
            case 10:days=30;
                   break;
            case 11:days=31;
                   break;
        }
        return days;
    }
}
