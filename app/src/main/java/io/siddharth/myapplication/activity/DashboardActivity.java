package io.siddharth.myapplication.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.adapter.DashboardPagerAdapter;
import io.siddharth.myapplication.communication.ChatClientThread;
import io.siddharth.myapplication.databinding.ActivityDashboardBinding;
import io.siddharth.myapplication.domain.model.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity {

    public static ChatClientThread chatClientThread;

    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(chatClientThread == null){
            chatClientThread = new ChatClientThread();
            chatClientThread.start();
        }
        super.onCreate(savedInstanceState);

        // 1. Initialize View Binding
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        
        EdgeToEdge.enable(this,
                SystemBarStyle.dark(ContextCompat.getColor(this, R.color.colorToolBar)),
                SystemBarStyle.light(ContextCompat.getColor(this, R.color.colorWhite), ContextCompat.getColor(this, R.color.colorWhite))
        );

        setContentView(binding.getRoot());

        // 2. Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // 3. Handle Back Press with Modern Dispatcher
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        setupToolbar();
        setupViewPager();
        setupBottomNavigation();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to close the application?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        binding.toolbar.toolbarTitle.setVisibility(View.VISIBLE);
        binding.toolbar.toolbarTitle.setText(getString(R.string.app_name));
    }

    private void setupViewPager() {
        DashboardPagerAdapter adapter = new DashboardPagerAdapter(this);
        binding.dashboardPager.setAdapter(adapter);
        binding.dashboardPager.setOffscreenPageLimit(3);

        binding.dashboardPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.dashBottomBar.getMenu().getItem(position).setChecked(true);
                updateToolbarTitle(position);
            }
        });
    }

    private void setupBottomNavigation() {
        binding.dashBottomBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tab_dash_student_list) {
                binding.dashboardPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.tab_dash_analytics) {
                binding.dashboardPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.tab_dash_support) {
                binding.dashboardPager.setCurrentItem(2);
                return true;
            }
            return false;
        });
    }

    private void updateToolbarTitle(int position) {
        switch (position) {
            case 0:
                binding.toolbar.toolbarTitle.setText(R.string.student_list);
                break;
            case 1:
                binding.toolbar.toolbarTitle.setText(R.string.analytics);
                break;
            case 2:
                binding.toolbar.toolbarTitle.setText(R.string.support);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // Intent to SettingActivity could go here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
