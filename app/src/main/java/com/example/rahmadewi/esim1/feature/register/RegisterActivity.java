package com.example.rahmadewi.esim1.feature.register;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.base.mvp.MvpActivity;
import com.example.rahmadewi.esim1.check.MyService;
import com.example.rahmadewi.esim1.check.SessionManager;
import com.example.rahmadewi.esim1.feature.login.LoginActivity;
import com.example.rahmadewi.esim1.feature.main.MainActivity;
import com.example.rahmadewi.esim1.models.user.User;

import butterknife.BindView;

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterView, View.OnClickListener {

    ProgressDialog pDialog;

    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.link_login)
    TextView logIn;
    @BindView(R.id.txtRegisterUsername)
    EditText txtUsername;
    @BindView(R.id.txtRegisterPassword1)
    EditText txtPassword;
    @BindView(R.id.txtRegisterPassword2)
    EditText txtPassword1;
    @BindView(R.id.txtRegisterNomor)
    EditText txtNomor;

    String username, password1, password2,nomor;

    SessionManager session;

    @Override
    protected RegisterPresenter cretePresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(getApplicationContext());

        btnRegister.setOnClickListener(this);
        logIn.setOnClickListener(this);
    }

    @Override
    public void showLoading() {
        pDialog = new ProgressDialog(RegisterActivity.this);
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
            session.createLoginSession(username, password1);
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(nomor, null, "Silakan bergabung dalam grup WhatsApp E-SIM untuk membagikan nilai simulasi ke grup. \n Terima kasih. \n https://chat.whatsapp.com/JGM5jQIw8wG94WNl3JHI5Q", pi,null);

            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Berhasil")
                    .setMessage("User berhasil dibuat, anda akan menerima SMS untuk masuk ke grup WhatsApp")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            finish();

                            overridePendingTransition(0, 0);
                            startActivity(i);
                        }
                    }).show();
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
            case R.id.btnRegister:
                if (txtUsername.getText().toString().trim().equals("")) {
                    txtUsername.setError("Username is required!");
                } else if (txtPassword.getText().toString().trim().equals("")) {
                    txtPassword.setError("Password is required!");
                } else if (txtPassword1.getText().toString().trim().equals("")) {
                    txtPassword1.setError("Retype Your Password!");
                } else {
                    username = txtUsername.getText().toString();
                    password1 = txtPassword.getText().toString();
                    password2 = txtPassword1.getText().toString();
                    nomor = txtNomor.getText().toString();

                    if (!password1.equals(password2)) {
                        txtPassword1.setError("Password didn't match!");
                    } else {
                        if(!MyService.isConnected(RegisterActivity.this)){
                            getDataFail("No Internet Connection");
                        }else {
                            presenter.register(username, password1, nomor);
                        }
                    }
                }
                break;
            case R.id.link_login:
                presenter.moveHome(RegisterActivity.this, LoginActivity.class);
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
