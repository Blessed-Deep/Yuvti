package com.softblxgenesis.yuvti.MenuOptions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.softblxgenesis.yuvti.R;
public class AboutActivity extends AppCompatActivity {
    private int i=0;
    private ImageView imageView;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==100){
                i=0;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imageView=(ImageView) findViewById(R.id.icoyuvti);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i==0){
                    ++i;
                    handler.sendEmptyMessageDelayed(100,3000); // 3000 equal 3sec , you can set your own limit of secs
                }else if(i==3){
                    Toast.makeText(AboutActivity.this, "Programmed by D BLESSED\nCopyright © SoftblxGenesis\nAll rights reserved" , Toast.LENGTH_LONG).show();
                    i=0;
                    handler.removeMessages(100);
                }else
                    ++i;
            }
        });



    }
}