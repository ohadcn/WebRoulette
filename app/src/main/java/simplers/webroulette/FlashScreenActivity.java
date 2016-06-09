package simplers.webroulette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FlashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
        finish();
    }
}
