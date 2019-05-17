package com.example.mezilajanm;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mezilajanm.fragments.BankAtmFragment;
import com.example.mezilajanm.fragments.HomeFragment;
import com.example.mezilajanm.fragments.TestFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mFrameLayout = findViewById(R.id.flMain);

        Fragment home = new HomeFragment();
        setFragment(home);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Fragment home = new HomeFragment();
                        setFragment(home);
                        return true;
                    case R.id.action_bank:
                        Fragment bank = new BankAtmFragment();
                        setFragment(bank);
                        return true;
                    case R.id.action_info:
                        Fragment test= new TestFragment();
                        setFragment(test);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flMain, fragment).commit();
    }
}
