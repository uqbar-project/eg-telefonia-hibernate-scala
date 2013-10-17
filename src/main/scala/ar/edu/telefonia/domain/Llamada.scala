package ar.edu.telefonia.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Llamada {

	var id: java.lang.Long = _  // OJO con el scala.Long que define un 0
	var origen: Abonado = _
	var destino: Abonado = _
	var duracion: Int = _

	@Id	@GeneratedValue def getId() = id
	def setId(_id: Long) = id = _id
	
	@ManyToOne def getOrigen() = origen
	def setOrigen(_origen: Abonado) = origen = _origen
	
	@ManyToOne def getDestino() = destino
	def setDestino(_destino : Abonado) = destino = _destino
	
	@Column def getDuracion() = duracion
	def setDuracion(_duracion: Int) = duracion = _duracion
	
	def this(_origen: Abonado, _destino:Abonado, _duracion:Int) {
	  this()
	  origen = _origen
	  destino = _destino
	  duracion = _duracion
	}
}