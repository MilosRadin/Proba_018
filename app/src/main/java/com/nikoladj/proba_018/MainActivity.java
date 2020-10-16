package com.nikoladj.proba_018;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private List<String> drawerItems;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private AlertDialog dialog;
    public static final int NOTIF_ID = 101;
    public static final String NOTIF_CHANNEL_ID = "nas_notif_kanal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        fillData();
        setupToolbar();
        setupDrawer();
        setupFab();
        showProbaFragment();
    }

    private void fillData(){
        drawerItems = new ArrayList<>();
        drawerItems.add("Toast");
        drawerItems.add("Snackbar");
        drawerItems.add("Dialog");
        drawerItems.add("Notification");
        drawerItems.add("Preferences");
    }

    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }

    private void setupDrawer(){
        drawerList = findViewById(R.id.left_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i) {
                    case 0:
                        title = "Toast";
                        showToast();
                        break;
                    case 1:
                        title = "Snackbar";
                        showSnackbar();
                        break;
                    case 2:
                        title = "Dialog";
                        showDialog();
                        break;
                    case 3:
                        title = "Notification";
                        showNotification();
                        break;
                    case 4:
                        title = "Preferences";
                        showPrefs();
                        break;
                    default:
                        break;
                }
                //drawerList.setItemChecked(i, true);
                setTitle(title);
                drawerLayout.closeDrawer(drawerList);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(
                this,                           /* host Activity */
                drawerLayout,                   /* DrawerLayout object */
                toolbar,                        /* nav drawer image to replace 'Up' caret */
                R.string.app_name,           /* "open drawer" description for accessibility */
                R.string.app_name           /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle("");
                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle("");
                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
            }
        };
    }

    private void showToast(){
        Toast.makeText(this, "Ovo je Toast", Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(){
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.root), "Ovo je Snackbar", Snackbar.LENGTH_SHORT);
        snackbar.setAction("U Redu", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void showDialog(){
        if (dialog == null){
            dialog = new NasDijalog(this).prepareDialog();
        } else {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
        dialog.show();
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIF_CHANNEL_ID);
        builder.setContentTitle("Nova Notifikacija")
                .setContentText("Ovo je tekst notifikacije")
                .setSmallIcon(R.drawable.ic_cake_black_24dp);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIF_ID, builder.build());
    }

    private void showPrefs(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PrefsFragment fragment = new PrefsFragment();
        transaction.replace(R.id.root, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, "Nas Notif Kanal", importance);
            channel.setDescription("Opis naseg kanala");
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] { 1000, 1000, 1000, 1000});

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setupFab(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar();
            }
        });
    }

    private void showProbaFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProbaFragment fragmentPrimer = new ProbaFragment();
        fragmentPrimer.setContent(":)");
        transaction.replace(R.id.root, fragmentPrimer);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // onOptionsItemSelected method is called whenever an item in the Toolbar is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                Toast.makeText(this, "Action create executed.", Toast.LENGTH_SHORT).show();
                //showPrefs();
                break;
            case R.id.action_update:
                Toast.makeText(this, "Action update executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete:
                Toast.makeText(this, "Action delete executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_replace:
                Toast.makeText(this, "Action replace executed.", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}