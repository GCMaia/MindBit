
package br.com.mindbit.controleacesso.gui;

import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.GuiUtil;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class CalendarActivity extends AppCompatActivity implements FabSpeedDial.MenuListener {
    private CalendarView calendar;
    private FabSpeedDial btn_addEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        SessaoUsuario sessao = SessaoUsuario.getInstancia();

        calendar = (CalendarView) findViewById(R.id.calendarView);
       // MenuItem itemAddEvento = (MenuItem) findViewById(R.id.adicionarEvento);

        startPerfilActivty();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

//        Menu menuActionButton = (Menu)  getMenuInflater().inflate(R.menu.menu, menu);
//        View view = navigationView.getHeaderView(0);
//
        FabSpeedDial fab = (FabSpeedDial) findViewById(R.id.actionButton);



        fab.setMenuListener(this);
//fab.onClick(null);
    }



    @Override
    public boolean onPrepareMenu(NavigationMenu navigationMenu) {
        return false;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.adicionarEvento:
                GuiUtil.exibirMsg(CalendarActivity.this, "eventoo");
                return true;
        }return false;
    }
    @Override
    public void onMenuClosed () {

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.adicionarEvento){
            addEvento();
        }
        return true;
    }

    public void addEvento(){
        GuiUtil.exibirMsg(CalendarActivity.this, "CHAMAR ADD EVENTO");
    }

    public void startPerfilActivty(){
        startActivity(new Intent(this, PerfilActivity.class));
    }
}
