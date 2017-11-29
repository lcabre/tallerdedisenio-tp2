package ar.edu.unlam.smartshop.servicios;

import ar.edu.unlam.smartshop.daos.PivotTableDao;
import ar.edu.unlam.smartshop.modelos.PivotTable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("pivotTableServicio")
public class PivotTableServicioImpl implements PivotTableServicio {

	@Inject
	private PivotTableDao pivotTableDao;

	@Override
	public void save(PivotTable pivot) {
		pivotTableDao.save(pivot);
	}

	@Override
	public void update(PivotTable pivot) {
		pivotTableDao.update(pivot);
	}
}
