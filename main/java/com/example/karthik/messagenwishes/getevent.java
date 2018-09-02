package com.example.karthik.messagenwishes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class getevent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getevent);
        final EditText gettheevent=(EditText)findViewById(R.id.gettingevent);
        final Button submission=(Button)findViewById(R.id.sub);
        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.putExtra("THE_EVENT",gettheevent.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        });
    }

}
