package tcf.com.tcp.curriculumdesign2;

import android.Manifest;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import tcf.com.tcp.curriculumdesign2.databinding.ActivityMainBinding;
import tcf.com.tcp.curriculumdesign2.utils.PermissionUtils;
import tcf.com.tcp.curriculumdesign2.utils.WebApiUtils;

public class MainActivity extends AppCompatActivity {
    public NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration
                = new AppBarConfiguration.Builder(
                        R.id.navigation_information,
                        R.id.navigation_reserve,
                        R.id.navigation_myself).build();
        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this,
                navController, appBarConfiguration);
        //导航控制器navController与AppBar配置挂钩
        NavigationUI.setupWithNavController(navView, navController);
        PermissionUtils.requestPermissions(this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //导航控制器navController与BottomNavigationView对象navView挂钩
    }
}