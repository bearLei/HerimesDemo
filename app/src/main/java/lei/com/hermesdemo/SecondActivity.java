package lei.com.hermesdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import impl.IUserManager;
import unit.ProcessManager;
import unit.UserManager;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class SecondActivity extends Activity {
    IUserManager userManager;
    Button getPerson;
    TextView showPerson;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        ProcessManager.getInstance().connect(this);
        getPerson = findViewById(R.id.getPerson);
        showPerson = findViewById(R.id.showPerson);
        getPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager = ProcessManager.getInstance().getObjectInstance(UserManager.class);
                String s = userManager.getPerson().toString();
                Log.d("lei",s);
                showPerson.setText(userManager.getPerson().getName()+"--->"+userManager.getPerson().getPassWord());
            }
        });
    }
}
