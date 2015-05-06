package ech98.echen.stackable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private TextView userSignupLink;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSignupLink = (TextView) findViewById(R.id.userSignupLink);
        userSignupLink.setPaintFlags(userSignupLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mLoginButton = (Button) findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main_AuthedActivity.class);
                startActivity(intent);
            }
        });
    }
}
