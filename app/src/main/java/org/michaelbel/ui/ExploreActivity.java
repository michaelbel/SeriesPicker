package org.michaelbel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.michaelbel.app.AndroidExtensions;
import org.michaelbel.material.widget2.FragmentsPagerAdapter;
import org.michaelbel.rest.model.Show;
import org.michaelbel.seriespicker.R;
import org.michaelbel.ui.fragment.NowPlayingShowsFragment;
import org.michaelbel.ui.fragment.PopularShowsFragment;
import org.michaelbel.ui.fragment.TopRatedShowsFragment;

/**
 * Date: 19 MAR 2018
 * Time: 13:20 MSK
 *
 * @author Michael Bel
 */

public class ExploreActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidExtensions.setScrollFlags(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.ExploreShows);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new PopularShowsFragment(), R.string.Popular);
        adapter.addFragment(new NowPlayingShowsFragment(), R.string.NowPlaying);
        adapter.addFragment(new TopRatedShowsFragment(), R.string.TopRated);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.primaryText));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.secondaryText), ContextCompat.getColor(this, R.color.primaryText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*menu.add(R.string.Tune)
              .setIcon(R.drawable.ic_tune)
              .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
              .setOnMenuItemClickListener(item -> {
                  return true;
              });*/

        menu.add(R.string.Search)
            .setIcon(R.drawable.ic_search)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                startActivity(new Intent(ExploreActivity.this, SearchActivity.class));
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    public void startShow(Show show) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("show", show);
        startActivity(intent);
    }
}