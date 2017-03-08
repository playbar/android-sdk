package com.bar.gv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bar.R;


public class MainActivity extends Activity {
    private Button button1, button2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gv_activity_main);
        findView();
        setListener();
    }

    private void setListener() {
        button1.setOnClickListener(mylistener);
        button2.setOnClickListener(mylistener);
    }

    View.OnClickListener mylistener  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent ;
            switch (view.getId()){
                case R.id.button1 :
                    intent = new Intent(MainActivity.this , FirstActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button2 :
                    intent = new Intent(MainActivity.this , SecondActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private void findView() {
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
    }
}
