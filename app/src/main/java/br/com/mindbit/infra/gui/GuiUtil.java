package br.com.mindbit.infra.gui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.gui.LoginActivity;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

public class GuiUtil {

    public static void exibirMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void exibirSaudacao(Activity activity){
        Context context;
        context = LoginActivity.getContext();
        Toast.makeText(activity, context.getString(R.string.login_sucess)+" "+
                SessaoUsuario.getInstancia().getPessoaLogada().getNome()+"!", Toast.LENGTH_LONG).show();
    }



    public static final Uri IMAGEM_PADRAO = Uri.parse("android.resource://br.com.mindbit/"+ R.drawable.ic_person);

    private static GuiUtil instanciaGuiUtil = new GuiUtil();
    private GuiUtil(){}
    public static GuiUtil getInstancia() {
        return instanciaGuiUtil;
    }

}