package com.example.rahmadewi.esim1.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.check.SessionManager;
import com.example.rahmadewi.esim1.feature.dashboardFragment.DashboardFragment;
import com.example.rahmadewi.esim1.feature.login.LoginActivity;
import com.example.rahmadewi.esim1.feature.mainFragment.MainFragment;
import com.example.rahmadewi.esim1.feature.updateFragment.UpdateFragment;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;

import java.util.HashMap;

import butterknife.BindView;

public class MainActivity extends MvpActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener,MainView {

    SessionManager session;

    @Override
    protected MainPresenter cretePresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        TextView user = (TextView) findViewById(R.id.txtLogin);

        if(session.isLoggedIn()) {
            HashMap<String, String> userData = session.getUserDetails();
            String username = userData.get(SessionManager.KEY_USERNAME);
            System.out.println("ini username : "+username);
            //user.setText(username);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        presenter.setData();

        moveFragmentMain();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            moveFragmentMain();
        } else if (id == R.id.nav_dashboard) {
            moveFragmentDashboard();
        } else if (id == R.id.nav_update) {
            moveFragmentUpdate();
        } else if (id == R.id.nav_logout) {
            logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void moveFragmentMain() {
        final String tag = MainFragment.class.getSimpleName();
        final Fragment fragment = new MainFragment();

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void moveFragmentDashboard() {
        final String tag = DashboardFragment.class.getSimpleName();
        final Fragment fragment = DashboardFragment.newInstance();

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void moveFragmentUpdate() {
        final String tag = UpdateFragment.class.getSimpleName();
        final Fragment fragment = UpdateFragment.newInstance();

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.commit();
    }

    private void logout(){
        overridePendingTransition(0, 0);
        finish();
        session.logoutUser();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivity(i);
    }
}
