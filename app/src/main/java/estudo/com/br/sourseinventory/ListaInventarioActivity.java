package estudo.com.br.sourseinventory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import estudo.com.br.sourseinventory.adapter.InventarioAdapter;
import estudo.com.br.sourseinventory.dao.InventarioDAO;
import estudo.com.br.sourseinventory.modelo.Dados;

public class ListaInventarioActivity extends AppCompatActivity {

    private ListView listaInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inventario);

        listaInventario = (ListView) findViewById(R.id.lista_inventario);

        listaInventario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long l) {//MONITORA O CLIC NO BOTAO
                Dados dados = (Dados) listaInventario.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaInventarioActivity.this, FormularioActivity.class);//CHAMA OUTRA TELA
                intentVaiProFormulario.putExtra("dados", dados);
                startActivity(intentVaiProFormulario);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaInventarioActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);

            }
        });


        registerForContextMenu(listaInventario);
    }

    private void carregaLista() {
        InventarioDAO dao = new InventarioDAO(this);
        List<Dados> dados =  dao.buscaDados();
        dao.close();

        InventarioAdapter adapter = new InventarioAdapter(this, dados);
        listaInventario.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Dados dados = (Dados) listaInventario.getItemAtPosition(info.position);

        MenuItem itemSite = menu.add("Sinopse");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = dados.getImdb();
        if (!site.startsWith("http://")){
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem excluir =  menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                InventarioDAO dao = new InventarioDAO(ListaInventarioActivity.this);
                dao.exclui(dados);
                dao.close();
                carregaLista();

                return false;
            }
        });
    }
}
