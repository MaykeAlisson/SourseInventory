package estudo.com.br.sourseinventory.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import estudo.com.br.sourseinventory.ListaInventarioActivity;
import estudo.com.br.sourseinventory.R;
import estudo.com.br.sourseinventory.modelo.Dados;

/**
 * Created by mayke on 19/11/2017.
 */

public class InventarioAdapter extends BaseAdapter {
    private final List<Dados> dados;
    private final Context context;

    public InventarioAdapter(Context context , List<Dados> dados) {
        this.context = context;
        this.dados = dados;
    }

    @Override
    public int getCount() {
        return dados.size();
    }// INFORMA O TAMANHO DA LISTA

    @Override
    public Object getItem(int position) {
        return dados.get(position);
    }// DEVOLVE A POSIÇOES DA LISTA

    @Override
    public long getItemId(int position) {
        return dados.get(position).getId();
    }// DEVOLVE O ID DE CADA ITEM DA LISTA

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Dados dado = dados.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);// INFLA O LAYOUT LIST_ITEM NA TELA
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, viewGroup, false);

        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);// CAPTURA O ID NOME, PARA PREENXER A LIST VIEW
        campoNome.setText(dado.getNome());

        TextView campoTipo = (TextView) view.findViewById(R.id.item_tipo);// CAPTURA O ID TIPO, PARA PREENCHER A LIST VIEW
        campoTipo.setText(dado.getTipo());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);// CAPTURA O ID FOTO , PARA PREENCHER A LIST VIEW
        String caminhoFoto = dado.getCaminhoFoto();
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        return view;
    }


}
