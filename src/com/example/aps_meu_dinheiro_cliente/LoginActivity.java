package com.example.aps_meu_dinheiro_cliente;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.aps_meu_dinheiro_cliente.entidade.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText ed1, ed2;
	Usuario c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);

		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(b1Listener);
		
		//Fecha esta activity
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

		//verifica a senha e login para entrar no sistema
		Button b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, CadastrarUsuarioActivity.class);
				startActivity(i);
				ed1.setText("");
				ed2.setText("");
			}
		});

	}
	
	OnClickListener b1Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			updateListView();
		}
	};

	private void updateListView() {
		String resourceURL = "http://10.0.2.2:2000/usuario/"+ed1.getText().toString()+"/login/"+ ed2.getText().toString();
		AsyncHttpClient httpClient = new AsyncHttpClient();
		
		httpClient.get(resourceURL, new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);

				try{
					c = new Usuario(obj.getLong("idusuario"), obj.getString("login"), obj.getString("senha"));				
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if (c.getIdusuario() == 0 && c.getLogin().equals("sem login") && c.getSenha().equals("sem senha")){
					Builder alertDialogBuilder = new Builder(LoginActivity.this);
					alertDialogBuilder.setTitle("Mensagem");
					alertDialogBuilder.setMessage("Login ou Senha incorreto!");
					alertDialogBuilder.setCancelable(false);
					
					//botao Ok
					alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							dialog.cancel();
						}
					});
					AlertDialog dialog = alertDialogBuilder.create();
					dialog.show();
				}else{
					dialogBuilder.setMessage("Usuario e senha corretos!");
					dialogBuilder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent i = new Intent(LoginActivity.this, MenuActivity.class);
								i.putExtra("login", c.getLogin());
								i.putExtra("idusuario", c.getIdusuario());
								i.putExtra("senha", c.getSenha());
								startActivity(i);
								ed1.setText("");
								ed2.setText("");
							}
						});
				dialogBuilder.show();
				
				}
			}
		});	
	}
}
