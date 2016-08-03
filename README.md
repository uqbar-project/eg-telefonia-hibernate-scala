# Telefonía

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

![Solución](https://github.com/uqbar-project/eg-telefonia-hibernate-xtend/blob/master/docs/DER%20telefonia.png)
