package lei.com.hermesdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bean.RequestBean;
import impl.IUserManager;
import unit.Person;
import unit.ProcessManager;
import unit.UserManager;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessManager.getInstance().register(IUserManager.class);
                Person person =new Person("熊熊","123456");
                Log.d("lei","Main-->"+person.toString());
                UserManager.getInstance().setPerson(person);
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }
}
