package com.example.karthik.messagenwishes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class selectperson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectperson);
        final Intent fromwhere=getIntent();
        Button s=(Button)findViewById(R.id.sendbutton);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e=(EditText)findViewById(R.id.namep);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                if(fromwhere.getStringExtra("bid").equals("1"))
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Happy Birthday "+e.getText().toString());
                else
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Congratulations "+e.getText().toString());
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });




    }
}
