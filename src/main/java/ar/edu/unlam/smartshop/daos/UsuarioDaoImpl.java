package ar.edu.unlam.smartshop.daos;

import ar.edu.unlam.smartshop.modelos.TipoUusuario;
import ar.edu.unlam.smartshop.modelos.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
		session.save(usuario);
    }

	@Override
    @Transactional
    public void update(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		session.update(usuario);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = session.load(Usuario.class, new Integer(id));
		if(null != usuario){
			session.delete(usuario);
		}
    }

    @Override
    @Transactional
    public List list() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Usuario.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    @Transactional
    public Usuario getById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public Usuario getUser(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", usuario.getEmail()))
                .uniqueResult();
    }

    @Override
    @Transactional
    public Usuario getUserByMail(String email) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
        Hibernate.initialize(usuario.getEstablecimientos());
        Hibernate.initialize(usuario.getListaDeCompras());
        return usuario;
    }
}
