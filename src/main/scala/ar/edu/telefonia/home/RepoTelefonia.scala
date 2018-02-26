package ar.edu.telefonia.home

import javax.persistence.criteria.Predicate
import javax.persistence.{EntityManager, NoResultException, Persistence, PersistenceException}

import ar.edu.telefonia.domain._

object RepoTelefonia {

  private final def entityManagerFactory = Persistence.createEntityManagerFactory("Telefonia")

  def getEntityManager() = {
    entityManagerFactory.createEntityManager
  }

  def getAbonado(abonado: Abonado): Abonado = {
    getAbonado(abonado, false)
  }

  def getAbonado(unAbonado: Abonado, full: Boolean): Abonado = {
    val entityManager = this.getEntityManager
    // No pude compilar con los objetos from y where de criteriaBuilder
    val query = entityManager.createQuery("from Abonado a where a.nombre = :nombre")
    query.setParameter("nombre", unAbonado.nombre)
    /**
      * si indicamos qué tipo de fetch debemos hacer
      * esto no funciona bien con varias listas a la vez, una pena
      * if (full) {
      *from.fetch("facturas", JoinType.LEFT)
      *from.fetch("llamadas", JoinType.LEFT)
      * }
      *
      */
    try {
      val abonado = query.getSingleResult.asInstanceOf[Abonado]
      if (full) {
        abonado.llamadas.size
        abonado.facturas.size
      }
      return abonado
    } catch {
      case e: NoResultException => return null
    } finally {
      entityManager.close
    }
  }

  /**
    * Para actualizar o eliminar como la base es similar, lo que hacemos es
    * pasar un closure (un command) que se construye con un objeto bloque de Xtend.
    * También podríamos construir una clase con una interfaz execute(EntityManager, Abonado)
    * pero esto es menos burocrático.
    */
  def actualizarAbonado(abonado: Abonado): Unit =
    doInTransaction(abonado, (em: EntityManager, _abonado: Abonado) => em.merge(_abonado))

  def eliminarAbonado(abonado: Abonado): Unit =
    doInTransaction(abonado, (em: EntityManager, _abonado: Abonado) => em.remove(_abonado))


  def doInTransaction(abonado: Abonado, operation: (EntityManager, Abonado) => Unit): Unit = {
    val entityManager = this.getEntityManager
    try {
      entityManager.getTransaction.begin
      operation.apply(entityManager, abonado)
      entityManager.getTransaction.commit
    } catch {
      case e: PersistenceException => {
        e.printStackTrace
        entityManager.getTransaction.rollback
        throw new RuntimeException("Ha ocurrido un error. La operación no puede completarse.", e)
      }
    } finally {
      entityManager.close
    }
  }

}