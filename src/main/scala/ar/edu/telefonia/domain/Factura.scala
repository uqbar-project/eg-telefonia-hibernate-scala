package ar.edu.telefonia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Factura {
  	var id: java.lang.Long = _  // OJO con el scala.Long que define un 0
	var fecha : java.util.Date = _
	var totalPagado : java.math.BigDecimal = _
	var total : java.math.BigDecimal = _
	
	@Id	@GeneratedValue def getId() = id
	def setId(_id:Long) = id = _id
	
	@Column def getFecha() = fecha
	def setFecha(_fecha: java.util.Date) = fecha = _fecha
	
	@Column def getTotalPagado() = totalPagado
	def setTotalPagado(_totalPagado : java.math.BigDecimal) = totalPagado = _totalPagado
	
	@Column def getTotal() = total
	def setTotal(_total : java.math.BigDecimal) = total = _total
	
	def saldo() = totalPagado.subtract(total)

	def this(_fecha: java.util.Date, _totalPagado: Int, _total: Int) {
	  this()
	  fecha = _fecha
	  totalPagado = new java.math.BigDecimal(_totalPagado)
	  total = new java.math.BigDecimal(_total)
	}
}