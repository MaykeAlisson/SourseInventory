package estudo.com.br.sourseinventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import estudo.com.br.sourseinventory.dao.InventarioDAO;
import estudo.com.br.sourseinventory.modelo.Dados;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_FOTO = 567;
    private FormularioHelper helper; // ATRIBUTO HELPER
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//PRIMEIRAS ATIVIDADES A SEREM CRIADAS AO ABRIR ESTA TELA
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);// TRABALHA COM OS DADOS DO FORMULARIO

        Intent intent = getIntent();
        Dados dados = (Dados) intent.getSerializableExtra("dados");// CAPITURA O GET DO EXTRA
        if (dados != null){// VERIFICA SE O ALUNO E NULO
            helper.preencheFormulario(dados);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto); // MONITORA A AÇAO NO BOOTAO DE TIRAR FOTO
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_FOTO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// ABRE A FOTO QUE FOI TIRADA
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_FOTO) {
                helper.carregaFoto(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// MENU NO TOPO DO CABEÇALHO
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {// CAPTURA QUAL MENU FOI SELECIONADO
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Dados dados = helper.pegaDados();
                InventarioDAO inventarioDAO = new InventarioDAO(this);
                if (dados.getId() != 0) {
                    inventarioDAO.altera(dados);
                }else{
                    inventarioDAO.insere(dados);
                }
                inventarioDAO.close();
                Toast.makeText(FormularioActivity.this, dados.getNome() +" Salvo!",Toast.LENGTH_SHORT).show();//MENSAGEM QUE OS DADOS FORAM INSERIDOS

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
