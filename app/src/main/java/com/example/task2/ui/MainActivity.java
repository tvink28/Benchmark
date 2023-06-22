package com.example.task2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.task2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentAdapter adapter = new FragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.view_actionbar);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, this);
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tabLayoutMediator != null) {
            tabLayoutMediator.detach();
        }
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        final int textId;
        final int backgroundId;
        if (position == 0) {
            textId = R.string.tab_collections;
            backgroundId = R.drawable.tab_drawable_selector_1;
        } else {
            textId = R.string.tab_maps;
            backgroundId = R.drawable.tab_drawable_selector_2;
        }
        tab.setText(textId).view.setBackgroundResource(backgroundId);
    }
}
