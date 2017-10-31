package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.modelos.Establecimiento;

import java.util.List;

public interface EstablecimientoServicio {
    void save(Establecimiento producto);
    void update(Establecimiento producto);
    void delete(Integer id);
    List list();
    Establecimiento getById(Integer id);
}
