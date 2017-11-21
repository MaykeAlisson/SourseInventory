package estudo.com.br.sourseinventory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import estudo.com.br.sourseinventory.modelo.Dados;

/**
 * Created by mayke on 10/11/2017.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoTipo;
    private final EditText campoGenero;
    private final EditText campoImdb;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private  Dados dados;

    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoTipo = (EditText) activity.findViewById(R.id.formulario_tipo);
        campoGenero = (EditText) activity.findViewById(R.id.formulario_genero);
        campoImdb = (EditText) activity.findViewById(R.id.formulario_imdb);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        dados = new Dados();

    }

    public Dados pegaDados() {
        dados.setNome(campoNome.getText().toString());
        dados.setTipo(campoTipo.getText().toString());
        dados.setGenero(campoGenero.getText().toString());
        dados.setImdb(campoImdb.getText().toString());
        dados.setNota(Double.valueOf(campoNota.getProgress()));
        dados.setCaminhoFoto((String) campoFoto.getTag());

        return dados;
    }

    public void preencheFormulario(Dados dados) { // METODO QUE TRAS OS DADOS DO USUARIO SELECIONADO PARA EDITAR
        campoNome.setText(dados.getNome());
        campoTipo.setText(dados.getTipo());
        campoGenero.setText(dados.getGenero());
        campoImdb.setText(dados.getImdb());
        campoNota.setProgress((int) dados.getNota());
        carregaFoto(dados.getCaminhoFoto());
        this.dados = dados;
    }

    public void carregaFoto(String caminhoFoto) {
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);

        }
    }
}
