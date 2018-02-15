package ec.com.tpg.tpgnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ec.com.tpg.tpgnews.view.ContainerActivity;
import ec.com.tpg.tpgnews.view.CreateAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void irCreateAccount(View view)
    {
        Intent intent =new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


    public void login(View view)
    {
        Intent intent =new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

}
