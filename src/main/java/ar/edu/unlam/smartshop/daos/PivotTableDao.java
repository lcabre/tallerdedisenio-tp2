package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.PivotTable;

import java.util.List;

public interface PivotTableDao {
    void save(PivotTable pivotTable);
    void update(PivotTable pivotTable);
    void delete(Integer id);
    List list();
    PivotTable getById(Integer id);

    //List<PivotTable> findByIds(Integer[] listaProductosId);
}
