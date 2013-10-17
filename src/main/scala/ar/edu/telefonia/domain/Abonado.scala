package ar.edu.telefonia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import scala.collection.JavaConversions._
import javax.persistence.OneToMany
import javax.persistence.InheritanceType
import javax.persistence.FetchType
import javax.persistence.CascadeType
import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorValue
import javax.persistence.DiscriminatorType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_ABONADO", discriminatorType = DiscriminatorType.STRING)
abstract class Abonado {
  var id: java.lang.Long = _  // OJO con el scala.Long que define un 0
  var nombre: String = _
  var numero: String = _
  var facturas: java.util.List[Factura] = new java.util.ArrayList[Factura]()
  var llamadas: java.util.List[Llamada] = new java.util.ArrayList[Llamada]()

  @Id @GeneratedValue def getId() = id
  def setId(_id: Long) = id = _id

  @Column def getNombre() = nombre
  def setNombre(_nombre: String) = nombre = _nombre

  @Column def getNumero() = numero
  def setNumero(_numero: String) = numero = _numero

  @OneToMany(fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  def getFacturas() = facturas
  def setFacturas(_facturas: java.util.List[Factura]) = facturas = _facturas

  @OneToMany(fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  def getLlamadas() = llamadas
  def setLlamadas(_llamadas: java.util.List[Llamada]) = llamadas = _llamadas

  def costo(llamada: Llamada): Float

  def esMoroso() = deuda > 0

  def deuda(): Float = {
    facturas.foldLeft(new java.math.BigDecimal(0))((acum, factura) => acum.add(factura.saldo)).floatValue()
  }
  
  def agregarLlamada(llamada : Llamada) = llamadas.add(llamada)
}

@Entity
@DiscriminatorValue("RS")
class Residencial extends Abonado {

  override def costo(llamada: Llamada) = 2 * llamada.duracion

}

@Entity
@DiscriminatorValue("RU")
class Rural extends Abonado {

  var cantidadHectareas: Integer = _

  @Column def getCantidadHectareas() = cantidadHectareas
  def setCantidadHectareas(_cantidadHectareas: Integer) = cantidadHectareas = _cantidadHectareas

  def this(_cantidadHectareas: Integer) {
    this()
    cantidadHectareas = _cantidadHectareas
  }
  
  override def costo(llamada: Llamada) = 3 * llamada.duracion.max(5)

}

@Entity
@DiscriminatorValue("EM")
class Empresa extends Abonado {

  var cuit: String = _

  @Column def getCuit() = cuit
  def setCuit(_cuit: String) = cuit = _cuit

  def this(_cuit: String) {
    this()
    cuit = _cuit
  }

  override def costo(llamada: Llamada) = 1 * llamada.duracion.min(3)

  override def esMoroso() = facturas.size > 3

}
