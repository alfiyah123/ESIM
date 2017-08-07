package com.example.rahmadewi.esim1.feature.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.check.MyService;
import com.example.rahmadewi.esim1.check.SessionManager;
import com.example.rahmadewi.esim1.feature.main.MainActivity;
import com.example.rahmadewi.esim1.feature.register.RegisterActivity;
import com.example.rahmadewi.esim1.models.user.User;

import butterknife.BindView;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    ProgressDialog pDialog;

    @BindView(R.id.txtLoginUsername)
    EditText txtUsername;

    @BindView(R.id.txtLoginPassword)
    EditText txtPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.link_signup)
    TextView signUp;

    SessionManager session;

    String username, password;

    @Override
    protected LoginPresenter cretePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn())
        {
            presenter.moveHome(LoginActivity.this, MainActivity.class);
        }

        btnLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void showLoading() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please wait ...");
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
    }

    @Override
    public void getDataSuccessUser(User user) {
        if(user.getCode() == 200){
            session.createLoginSession(username, password);
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            finish();

            overridePendingTransition(0, 0);
            startActivity(i);
        }else{
            getDataFail("Username dan Password tidak sesuai");
        }
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void moveToHome(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (txtUsername.getText().toString().trim().equals("")) {
                    txtUsername.setError("Username is required!");
                } else if (txtPassword.getText().toString().trim().equals("")) {
                    txtPassword.setError("Password is required!");
                } else {
                    username = txtUsername.getText().toString();
                    password = txtPassword.getText().toString();

                    if(!MyService.isConnected(LoginActivity.this)){
                        getDataFail("No Internet Connection");
                    }else {
                        presenter.login(username, password);
                    }
                }
                break;
            case R.id.link_signup:
                presenter.moveHome(LoginActivity.this, RegisterActivity.class);
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
