package ar.edu.unlam.smartshop;

import ar.edu.unlam.smartshop.controladores.BusquedaController;
import ar.edu.unlam.smartshop.modelos.*;
import ar.edu.unlam.smartshop.modelos.api.Distance;
import ar.edu.unlam.smartshop.modelos.api.MapAPI;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestBusquedaCercania {

    @Test
    @Rollback
    public  void  alPasarUnProductoYUnaDireccionMeDevuelveElEstablecimientoMasCercanoAEseProducto(){

        Establecimiento masCercano = new Establecimiento();
        masCercano.setNombre("Mas Cernano");
        masCercano.setBarrio("Ramos Mejia");
        masCercano.setDireccion("Av. de Mayo");
        masCercano.setNumero(1000);

        Establecimiento intermedio = new Establecimiento();
        intermedio.setNombre("Mas Lejano");
        intermedio.setBarrio("Ramos Mejia");
        intermedio.setDireccion("Av. de Mayo");
        intermedio.setNumero(1500);

        Establecimiento masLejano = new Establecimiento();
        masLejano.setNombre("Mas Lejano");
        masLejano.setBarrio("Ramos Mejia");
        masLejano.setDireccion("Av. de Mayo");
        masLejano.setNumero(2500);

        Usuario loguedUser = mock(Usuario.class);
        Producto prod1 = mock(Producto.class);
        PivotTable pivot1 = mock(PivotTable.class);
        PivotTable pivot2 = mock(PivotTable.class);
        PivotTable pivot3 = mock(PivotTable.class);

        ArrayList<PivotTable> pivotTables = new ArrayList<>();
        pivotTables.add(pivot1);
        pivotTables.add(pivot2);
        pivotTables.add(pivot3);

        ListaCompras listaCompras = mock(ListaCompras.class);

        when(prod1.getPivotTables()).thenReturn(pivotTables);
        when(pivot1.getEstablecimiento()).thenReturn(masCercano);
        when(pivot2.getEstablecimiento()).thenReturn(intermedio);
        when(pivot3.getEstablecimiento()).thenReturn(masLejano);
        when(loguedUser.getFullAdress()).thenReturn("Ramos Mejia, Av. de Mayo 100");

        Establecimiento establecimientoMasCercano = MapAPI.getEstablecimientoMasCercano(prod1,loguedUser.getFullAdress());

        Assert.assertTrue(establecimientoMasCercano.equals(masCercano));
    }
}
