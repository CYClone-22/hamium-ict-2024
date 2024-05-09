package com.example.mentos.Home;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mentos.R;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PhotoAdapter adapter;
    private Handler handler = new Handler();
    private Runnable update;

    private int currentPage = 0;
    private static final int NUM_PAGES = 3; // 총 페이지 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        viewPager = findViewById(R.id.viewPager);
        List<Integer> photoList = new ArrayList<>();
        photoList.add(R.drawable.handsome1);
        photoList.add(R.drawable.handsome2);
        photoList.add(R.drawable.handsome3);
        adapter = new PhotoAdapter(photoList);
        viewPager.setAdapter(adapter);

        update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                
                handler.postDelayed(update, 4000);
            }
        };
        
        // 3초마다 페이지 전환 실행
        handler.postDelayed(update, 4000);

    }


}
