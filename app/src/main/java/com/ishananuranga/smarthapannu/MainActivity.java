package com.ishananuranga.smarthapannu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import Fragments.AccountFragment;
import Fragments.HomeFragment;
import Fragments.ProgressFragment;
import Interfaces.MainActivityChangeListener;

public class MainActivity extends AppCompatActivity implements MainActivityChangeListener {

    private TextView mTextMessage;
    private BottomNavigationView mainNavigation;
    private HomeFragment homeFragment;
    private ProgressFragment progressFragment;
    private AccountFragment accountFragment;
    private FrameLayout mainFrame;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    return true;
                case R.id.navigation_progress:
                    setFragment(progressFragment);
                    return true;
                case R.id.navigation_account:
                    setFragment(accountFragment);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrame = findViewById(R.id.main_frame);
        mTextMessage = findViewById(R.id.message);
        mainNavigation = findViewById(R.id.navigation);
        mainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragment();
        progressFragment = new ProgressFragment();
        accountFragment = new AccountFragment();


        setFragment(homeFragment);

        Slide slide = new Slide(Gravity.LEFT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(R.id.navigation, true);
        getWindow().setExitTransition(slide);
    }

    /*@Override
    public void replaceFragment(Fragment fragment) {

        TextView title = findViewById(R.id.text_view_title);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        //fragmentTransaction.addSharedElement(title, "title");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }*/

    @Override
    public void changeActivity(String provider_id, String title) {
        Intent intent = new Intent(getApplication(), PaperListActivity.class);
        intent.putExtra("provider_id",provider_id);
        intent.putExtra("title", title);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,bundle);

    }
}
