Se solicita crear un programa que liste marcas de motos, junto al precio promedio de todas las
motos publicadas en mercadolibre para cada marca. Para ello se utilizará la api publica de
mercadolibre.

Los pasos a realizar son los siguientes:

1) Obtener el id de la categoría de motos (no es necesario que el programa haga este
paso, pero si hay que hacerlo manualmente):
https://developers.mercadolibre.com.ar/es_ar/categorias-y-publicaciones

2) Una vez obtenido ese id (podemos hardcodear este id en el programa), lo utilizaremos
para listar todas las motos publicadas:
https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas

3) Para cada marca de moto (ej: Kawasaki, Yamaha, Honda) obtener el promedio de
precio de todas las motos de esa marca.
Para el cálculo de este promedio solo deben utilizarse las motos que sean nuevas (no
usadas).
Mercadolibre devuelve la información paginada (paginar hasta el registro 900), llamar
a cada página de forma secuencial se considera una solución correcta pero se valora el
uso de paralelismo.

4) Imprimir por consola una lista de marcas de motos junto con el promedio de precio
para cada marca.
Una vez realizado este ejercicio subirlo a un repositorio público de github (o similar) y
enviarnos la URL. También sirve que nos envíes el archivo comprimido con el proyecto.