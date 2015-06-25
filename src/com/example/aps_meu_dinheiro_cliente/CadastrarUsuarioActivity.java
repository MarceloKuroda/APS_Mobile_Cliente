package com.example.aps_meu_dinheiro_cliente;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
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

public class CadastrarUsuarioActivity extends Activity {

	EditText ed1, ed2;
	Button b1, b2;
	Usuario c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);

		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(b1ClickListener);

		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CadastrarUsuarioActivity.this.finish();
				ed1.setText("");
				ed2.setText("");
			}
		});

	}

	OnClickListener b1ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String resourceURL = "http://10.0.2.2:2000/usuario/"+ ed1.getText().toString() + "/login/"+ ed2.getText().toString();
			AsyncHttpClient httpClient = new AsyncHttpClient();

			httpClient.get(resourceURL, new JsonHttpResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers,	JSONObject obj) {
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
							CadastrarUsuarioActivity.this);

					try {
						c = new Usuario(obj.getLong("idusuario"), obj.getString("login"), obj.getString("senha"));
					} catch (JSONException e) {
						e.printStackTrace();
					}

					if (!(c.getIdusuario() == 0 && c.getLogin().equals("sem login") && c.getSenha().equals("sem senha")) || (ed1.getText().toString().equals(null) ||  ed2.getText().toString().equals(null))) {
						Builder alertDialogBuilder = new Builder(
								CadastrarUsuarioActivity.this);
						alertDialogBuilder.setTitle("Mensagem");
						alertDialogBuilder
								.setMessage("Usuario Invalido ou ja existe!");
						alertDialogBuilder.setCancelable(false);

						// botao Ok
						alertDialogBuilder.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});
						AlertDialog dialog = alertDialogBuilder.create();
						dialog.show();
					} else {
						String tLogin = ed1.getText().toString();
						String tSenha = ed2.getText().toString();

						String resourceURL = "http://10.0.2.2:2000/usuario";
						AsyncHttpClient httpClient = new AsyncHttpClient();

						// create Json object
						JSONObject params = new JSONObject();
						try {
							params.put("login", tLogin);
							params.put("senha", tSenha);

						} catch (JSONException e1) {
							e1.printStackTrace();
						}

						StringEntity entity = null;
						try {
							entity = new StringEntity(params.toString());
							// indicate that the message sent is a json file
							entity.setContentType(new BasicHeader(
									HTTP.CONTENT_TYPE, "application/json"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

						httpClient.post(CadastrarUsuarioActivity.this,
								resourceURL, entity, "application/json",
								new JsonHttpResponseHandler() {
									public void onSuccess(int statusCode,
											Header[] headers,
											JSONObject response) {
										showSuccess(response);
									};
								});
					}
				}
			});
		}
	};

	public void showSuccess(JSONObject response) {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				CadastrarUsuarioActivity.this);
		String login = "não deu certo";

		try {
			c = new Usuario(response.getLong("idusuario"), response.getString("login"), response.getString("senha"));
			login = response.getString("login");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dialogBuilder.setMessage("Usuario " + login
				+ " Cadastrado com Sucesso!");
		dialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ed1.setText("");
						ed2.setText("");
						Intent i = new Intent(CadastrarUsuarioActivity.this,
								MenuActivity.class);
						i.putExtra("login", c.getLogin());
						i.putExtra("idusuario", c.getIdusuario());
						i.putExtra("senha", c.getSenha());
						startActivity(i);
						CadastrarUsuarioActivity.this.finish();
					}
				});
		dialogBuilder.show();

	}

}
