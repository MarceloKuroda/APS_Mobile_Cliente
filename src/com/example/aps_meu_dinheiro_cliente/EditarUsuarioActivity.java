package com.example.aps_meu_dinheiro_cliente;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.aps_meu_dinheiro_cliente.entidade.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

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

public class EditarUsuarioActivity extends Activity {

	long idusuario;
	String login, senha;
	EditText ed1, ed2;
	Button b1, b2;
	Usuario c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_usuario);
		
		idusuario = (long) getIntent().getLongExtra("idusuario", 0);
		login = (String) getIntent().getStringExtra("login");
		senha = (String) getIntent().getStringExtra("senha");
		
		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		
		ed1.setText(login);
		ed2.setText(senha);
		
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(b1ClickListener);
		
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditarUsuarioActivity.this.finish();
			}
		});
	}
	
	OnClickListener b1ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
					if (ed1.getText().toString().equals("") ||  ed2.getText().toString().equals("") || ed1.getText().toString().equals(null) ||  ed2.getText().toString().equals(null)) {
						Toast.makeText(EditarUsuarioActivity.this, "Usuario Invalido!", Toast.LENGTH_SHORT).show();						
					} else {	
						String tLogin = ed1.getText().toString();
						String tSenha = ed2.getText().toString();
						
						String log = "";
						for(int i = 0; i < tLogin.length(); i++){
							if (!String.valueOf(tLogin.charAt(i)).equals(" ")){
								log += String.valueOf(tLogin.charAt(i));
							}
							if (String.valueOf(tLogin.charAt(i)).equals(" ")){
								log += String.valueOf("_");
							}
						}
						
						String resourceURL = "http://10.0.2.2:2000/usuario/" + idusuario + "/" + log + "/" + tSenha;
						
						AsyncHttpClient httpClient = new AsyncHttpClient();
						httpClient.put(resourceURL, new TextHttpResponseHandler() {
							@Override
							public void onSuccess(int status, Header[] headers, String responseString) {
								if(responseString != null && responseString.equals("usuario atualizado")) {				
									
									Toast.makeText(EditarUsuarioActivity.this, "Usuario editado com sucesso!", Toast.LENGTH_SHORT).show();
									Intent i = new Intent();
									i.putExtra("login", ed1.getText().toString());
									i.putExtra("senha", ed2.getText().toString());
									setResult(RESULT_OK, i);
									EditarUsuarioActivity.this.finish();
								}
								else {
									Toast.makeText(EditarUsuarioActivity.this, "Erro ao atualizar usuario!", Toast.LENGTH_SHORT).show();						
								}
							}
							
							@Override
							public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
								// TODO Auto-generated method stub
							}
						});
					}
			};
	};
	
}
