package com.toniel.githubuser;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.toniel.githubuser.adapter.SectionsPagerAdapter;

public class DetailUserActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
//    private ActivityMainBinding binding;
    ImageView imgAvatar;
    TextView tvUsername,tvName;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_follower,
            R.string.tab_text_following
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        setUser(user);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs,viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

    }

    private void setUser(User user){
        imgAvatar = findViewById(R.id.image_avatar);
        tvUsername = findViewById(R.id.tv_username);
        tvName = findViewById(R.id.tv_name);
        tvUsername.setText(user.getLogin());
        tvName.setText(user.getName());
        Glide.with(this)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(imgAvatar);
    }


}