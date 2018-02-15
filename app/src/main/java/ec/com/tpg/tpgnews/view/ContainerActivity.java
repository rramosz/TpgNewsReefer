package ec.com.tpg.tpgnews.view;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.view.fragment.HomeFragment;
import ec.com.tpg.tpgnews.view.fragment.ProfileFragment;
import ec.com.tpg.tpgnews.view.fragment.SearchFragment;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottom_bar= (BottomBar)  findViewById(R.id.bottombar);
        bottom_bar.setDefaultTab(R.id.tab_home);
        bottom_bar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        HomeFragment home_fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, home_fragment).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                addToBackStack(null).commit();

                        break;
                    case R.id.tab_profile:
                        ProfileFragment profile_fragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profile_fragment).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                addToBackStack(null).commit();
                        break;
                    case R.id.tab_search:
                        SearchFragment search_fragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, search_fragment).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                addToBackStack(null).commit();
                        break;
                }
            }
        });

    }




}
