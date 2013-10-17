package ar.edu.telefonia.home

import ar.edu.telefonia.domain.Abonado
import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.criterion.Restrictions
import ar.edu.telefonia.domain.Abonado
import ar.edu.telefonia.domain.Factura
import ar.edu.telefonia.domain.Llamada
import ar.edu.telefonia.domain.Residencial
import ar.edu.telefonia.domain.Rural
import ar.edu.telefonia.domain.Empresa

object HomeTelefonia {

  	private final def sessionFactory : SessionFactory = 
		new AnnotationConfiguration()
			.configure()
			.addAnnotatedClass(classOf[Factura])
			.addAnnotatedClass(classOf[Llamada])
			.addAnnotatedClass(classOf[Residencial])
			.addAnnotatedClass(classOf[Rural])
			.addAnnotatedClass(classOf[Empresa])
			.addAnnotatedClass(classOf[Abonado])
			.buildSessionFactory()

	def getAbonado(abonado : Abonado) : Abonado = {
		getAbonado(abonado, false)
  	}
  	
  	def getAbonado(abonado : Abonado, full: Boolean) : Abonado = {
  		var result : java.util.List[_] = null
		val session = sessionFactory.openSession()
		try {
			result = session
				.createCriteria(classOf[Abonado])
				.add(Restrictions.eq("nombre", abonado.nombre))
				.list()
				
			if (result.isEmpty) {
				null
			} else {
				var abonado = result.get(0).asInstanceOf[Abonado]
				if (full) {
				  abonado.facturas.size()
				  abonado.llamadas.size()
				}
				abonado
			}
		} catch {
		  	case e : HibernateException => throw new RuntimeException(e)
		} finally {
			session.close
		}
	}

	def actualizarAbonado(abonado : Abonado) {
		val session = sessionFactory.openSession
		try {
			session.beginTransaction
			session.saveOrUpdate(abonado)
			session.getTransaction.commit
			session.evict(abonado)
		} catch {
			case e : HibernateException => {
			  session.getTransaction.rollback
			  throw new RuntimeException(e)
			}
		} finally {
			session.close
		}
	}

}