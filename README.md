# Telefonía

[![Build Status](https://www.travis-ci.org/uqbar-project/eg-telefonia-hibernate-scala.svg?branch=master)](https://www.travis-ci.org/uqbar-project/eg-telefonia-hibernate-scala)

## Prerrequisitos
Necesitás instalar un motor de base de datos relacional (te recomendamos [MySQL](https://www.mysql.com/) que es OpenSource y gratuito).
También necesitás un IDE o entorno para programar en Scala, este proyecto necesita [IntelliJ Community](https://www.jetbrains.com/idea/) que es gratuito junto con el [plugin de Scala](https://www.jetbrains.com/help/idea/discover-intellij-idea-for-scala.html)

## Objetivo
El ejemplo de [abonados de una empresa telefónica](https://sites.google.com/site/utndesign/material/guia-de-ejercicios/guia-modelado-datos/orm_telefonia) muestra cómo mapear relaciones de herencia, one-to-many y many-to-one en Hibernate.

## Cómo correrlo

* BASE DE DATOS: En MySQL, crear una base de datos telefonia.
* SOLUCION: En Scala. Cuenta con JUnit tests para probar el dominio en forma aislada

## Configuraciones
Previamente, entrá al recurso hibernate.cfg.xml (Ctrl + Shift + R > tipeá hibernate y te aparece) y 
cambiá la contraseña de root de tu base

``` xml
<property name="hibernate.connection.password">xxxxx</property>
```

Si vas a ponerle otro nombre al esquema (base de datos), tenés que modificar la configuración del hibernate.cfg.xml 
para que apunte allí:

``` xml
<property name="hibernate.connection.url">jdbc:mysql://localhost/telefonia</property>
```

## Diagrama de entidad-relación

![Solución](https://github.com/uqbar-project/eg-telefonia-hibernate-xtend/blob/master/docs/DER.png)
