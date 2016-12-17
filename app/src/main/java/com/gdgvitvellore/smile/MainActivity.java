package com.gdgvitvellore.smile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout fragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentHolder = (FrameLayout)findViewById(R.id.main_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new HomePageFragment(),"Home Page").commit();
    }
}
