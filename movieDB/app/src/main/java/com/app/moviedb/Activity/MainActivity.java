package com.app.moviedb.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.moviedb.Fragment.MoviesFragment;
import com.app.moviedb.Fragment.ProfileFragment;
import com.app.moviedb.Fragment.TvFragment;
import com.app.moviedb.Utils.Message;
import com.app.moviedb.R;
import com.app.moviedb.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment fragmentMovies, fragmentTv, fragmentProfile, fragmentActive;

    private int bottomNavigationViewHeight;
    private boolean doubleBack = false;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        registerHandlers();
        attachFirstFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected void init() {
        fragmentManager = getSupportFragmentManager();
        activityMainBinding.bottomNavigationAcMain.setItemIconTintList(null);
    }

    protected void registerHandlers() {
        activityMainBinding.bottomNavigationAcMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_movie:
                        fragmentMovies = fragmentManager.findFragmentByTag("fragmentMovies");
                        fragmentManager.beginTransaction()
                                .hide(fragmentActive)
                                .show(fragmentMovies)
                                .commit();
                        fragmentActive = fragmentMovies;

                        break;
                    case R.id.action_tv:
                        fragmentTv = fragmentManager.findFragmentByTag("fragmentTv");
                        if (fragmentTv == null) {
                            fragmentTv = TvFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .hide(fragmentActive)
                                    .add(R.id.frameL_inner_ac_main, fragmentTv, "fragmentTv")
                                    .commit();
                        } else {
                            fragmentManager.beginTransaction()
                                    .hide(fragmentActive)
                                    .show(fragmentTv)
                                    .commit();
                        }
                        fragmentActive = fragmentTv;

                        break;
                    case R.id.action_profile:
                        fragmentProfile = fragmentManager.findFragmentByTag("fragmentProfile");
                        if (fragmentProfile == null) {
                            fragmentProfile = ProfileFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .hide(fragmentActive)
                                    .add(R.id.frameL_inner_ac_main, fragmentProfile, "fragmentProfile")
                                    .commit();
                        } else {
                            fragmentManager.beginTransaction()
                                    .hide(fragmentActive)
                                    .show(fragmentProfile)
                                    .commit();
                        }
                        fragmentActive = fragmentProfile;
                        break;
                }
                return true;
            }
        });
    }

    protected void attachFirstFragment() {
        fragmentMovies = MoviesFragment.newInstance();
        fragmentManager.beginTransaction().add(R.id.frameL_inner_ac_main, fragmentMovies, "fragmentMovies").commit();
        fragmentActive = fragmentMovies;
    }


    @Subscribe
    public void animateBottomView(Message message) {
        if (message.getStateNavigationAnimation() == 1) {
            bottomNavigationViewHeight = activityMainBinding.bottomNavigationAcMain.getHeight();
            activityMainBinding.bottomNavigationAcMain
                    .animate()
                    .translationY(bottomNavigationViewHeight)
                    .setDuration(100)
                    .start();
        } else if (message.getStateNavigationAnimation() == 2) {
            activityMainBinding.bottomNavigationAcMain
                    .animate()
                    .translationY(0)
                    .setDuration(360)
                    .start();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) fragmentManager.popBackStack();
        else {
            if (doubleBack) {
                super.onBackPressed();
            }
            doubleBack = true;
            Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBack = false;
                }
            }, 2000);
        }
    }
}
