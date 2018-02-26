package ar.edu.telefonia.home

import ar.edu.telefonia.domain._
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions
import org.hibernate.{HibernateException, SessionFactory}

object HomeTelefonia {

  	private final def sessionFactory : SessionFactory = 
		new Configuration()
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
				val abonado = result.get(0).asInstanceOf[Abonado]
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