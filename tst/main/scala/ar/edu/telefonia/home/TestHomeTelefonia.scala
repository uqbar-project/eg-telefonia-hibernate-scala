package ar.edu.telefonia.home

import ar.edu.telefonia.domain.Abonado
import ar.edu.telefonia.domain.Residencial
import ar.edu.telefonia.domain.Factura
import org.junit.Before
import ar.edu.telefonia.domain.Rural
import ar.edu.telefonia.domain.Llamada
import ar.edu.telefonia.domain.Empresa
import org.junit.Test
import org.junit.Assert
import org.hibernate.HibernateException
import org.hibernate.LazyInitializationException

class TestHomeTelefonia {

  var walterWhite: Abonado = _
  var jessePinkman: Abonado = _
  var homeTelefonia = HomeTelefonia
  var llamada1: Llamada = new Llamada(walterWhite, jessePinkman, 10)

  @Before
  def init() {
    walterWhite = new Residencial()
    walterWhite.nombre = "Walter White"
    walterWhite.numero = "46710080"
    walterWhite.agregarFactura(new Factura(new java.util.Date(10, 1, 109), 500, 240))
    walterWhite.agregarFactura(new Factura(new java.util.Date(10, 1, 111), 1200, 600))

    jessePinkman = new Rural(100)
    jessePinkman.nombre = "Jesse Pinkman"
    jessePinkman.numero = "45673887"
    jessePinkman.agregarFactura(new Factura(new java.util.Date(5, 5, 113), 1200, 1200))

    var ibm: Abonado = new Empresa("30-50396126-8")
    ibm.nombre = "IBM"
    ibm.numero = "47609272"

    createIfNotExists(jessePinkman)
    val existeIBM = createIfNotExists(ibm)
    val existeWalterWhite = createIfNotExists(walterWhite)

    jessePinkman = homeTelefonia.getAbonado(jessePinkman, true)
    ibm = homeTelefonia.getAbonado(ibm, true)
    walterWhite = homeTelefonia.getAbonado(walterWhite, true)

    // El update lo tenemos que hacer por separado por las referencias circulares
    if (!existeWalterWhite) {
      var llamada2: Llamada = new Llamada(walterWhite, ibm, 2)
      walterWhite.agregarLlamada(llamada1)
      walterWhite.agregarLlamada(llamada2)
      homeTelefonia.actualizarAbonado(walterWhite)
    }

    if (!existeIBM) {
      ibm.agregarLlamada(new Llamada(ibm, jessePinkman, 5))
      homeTelefonia.actualizarAbonado(ibm)
    }
  }

  def createIfNotExists(abonado: Abonado) = {
    val existe = homeTelefonia.getAbonado(abonado) != null
    if (!existe) {
      homeTelefonia.actualizarAbonado(abonado)
    }
    existe
  }

  @Test
  def walterWhiteTiene2Llamadas() {
    var walterWhiteBD = homeTelefonia.getAbonado(walterWhite, true)
    var llamadasDeWalterWhite = walterWhiteBD.llamadas
    Assert.assertEquals(2, llamadasDeWalterWhite.size)
  }

  @Test(expected = classOf[LazyInitializationException])
  def walterWhiteTiene2LlamadasSinSesionHibernate() {
    var walterWhiteBD = homeTelefonia.getAbonado(walterWhite, false)
    var llamadasDeWalterWhite = walterWhiteBD.llamadas.size
  }

  @Test
  def deudaDeWalterWhite() {
    var walterWhiteBD = homeTelefonia.getAbonado(walterWhite, true)
    Assert.assertEquals(860, walterWhiteBD.deuda, 0.1)
  }

  @Test
  def walterWhiteCostoDeLlamada1() {
    var walterWhiteBD = homeTelefonia.getAbonado(walterWhite, true)
    Assert.assertEquals(20, walterWhiteBD.costo(llamada1), 0.1)
  }

}