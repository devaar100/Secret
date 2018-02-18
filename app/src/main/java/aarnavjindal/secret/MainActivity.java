package aarnavjindal.secret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "App has begun", Toast.LENGTH_LONG).show();
             Intent i=new Intent(MainActivity.this, aarnavjindal.secret.SecretService.class);
                startService(i);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i=new Intent(MainActivity.this, aarnavjindal.secret.SecretService.class);
        stopService(i);
    }
}
