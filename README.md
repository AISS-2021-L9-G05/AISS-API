# API REST Teléfonos y Marcas

Trabajo de implementación de la asignatura AISS

La API REST estará formada por dos recursos que permitirán manipular marcas y teléfonos respectivamente. 

El contrato de servicios de marcas se detalla a continuación.

### Recurso Phone ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET |  /phones | Devuelve todas los teléfonos de la aplicación. •	Es posible ordenar los teléfonos por el nombre, el precio, la fecha de salida con el parámetro de query “order”, que acepta los valores “name”, “-name”, “price”, “-price”, "releaseDate" o "-releaseDate". •	También es posible filtrar los teléfonos devueltas con el parámetro de query “q”, que devuelve aquellos teléfonos cuyo nombre, precio, fecha de salida, tamaño o resolución contengan la cadena enviada (ignorando mayúsculas y minúsculas).|
| GET | /phones/{phoneId}  |  Devuelve el teléfono con id=phoneId. Si el teléfono no existe devuelve un “404 Not Found”. |
| POST | /phones | Añade un nuevo teléfono cuyos datos se pasan en el cuerpo de la petición en formato JSON (no se debe pasar id, se genera automáticamente). Si el nombre del teléfono no es válido (null o vacío) devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido del teléfono. |
| PUT | /phones  | Actualiza el teléfono cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id de el teléfono). Si el teléfono no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /phones/{phoneId}  |  Elimina el teléfono con id=phoneId. Si el teléfono no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|

Cada **teléfono** tiene un identificador, _nombre, precio, fecha de salida, tamaño y resolución_. La representación JSON del recurso es:

```cpp
{
	"id":"p0",
	"title":"Samsung Galaxy A52",
	"price":"349,90 €",
	"releaseDate":"17-03-2021",
	"size":"159,9 x 75,1 x 8,4 mm",
	"resolution":"2.400 x 1.080 px"
}
```


### Recurso Brand ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET | /brands  | Ver todas las marcas existentes. •	Es posible ordenar las marcas por nombre con el parámetro de query “order”, que solo acepta dos valores, “name” o “-name”. •	También es posible filtrar las marcas devueltas con dos parámetros de query: “isEmpty”, que devuelve marcas sin teléfonos si vale “true” o marcas con teléfonos si vale “false”; “name”, que devuelve las marcas cuyo nombre coincida exactamente con el valor del parámetro. |
| GET | /brands/{brandId} | Devuelve la marca con id=brandId. Si la marca no existe devuelve un “404 Not Found”. |
| POST | /brands | Añadir una nueva marca. Los datos de la marca (nombre, fecha de fundación y teléfonos vendidos) se proporcionan en el cuerpo de la petición en formato JSON. Las teléfonos de la marca no se pueden incluir aquí, para ello se debe usar la operación POST específica para añadir un teléfono a una marca (a continuación). Si el nombre de la marca no es válido (nulo o vacío), o se intenta crear una marca con teléfonos, devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de la marca. |
| PUT | /brands | Actualiza la marca cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id de la marca).  Si la marca no existe, devuelve un “404 Not Found”. Si se intenta actualizar las teléfonos de la marca, devuelve un error “400 Bad Request”. Para actualizar las teléfonos se debe usar el recurso Phone mostrado previamente. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /brands/{brandId} | Elimina la marca con id=brandId. Si la marca no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| POST |  /brands/{brandId}/{phoneId} | Añade el teléfono con id=phoneId a la marca con id=brandId. Si la marca o el teléfono no existe, devuelve un “404 Not Found”. Si el teléfono ya está incluido en la marca devuelve un “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de la marca. |
| DELETE | /brands/{brandId}/{phoneId}  | Elimina el teléfono con id=phoneId de la marca con id=brandId. Si la marca o el teléfono no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|


Una **marca** tiene un _identificador, nombre, fecha de fundación, teléfonos vendidos y un conjunto de teléfonos_. La representación JSON de este recurso es:

```cpp
{
	"id":"b0",
	"name":"Samsung",
	"foundationDate":"01-03-1938",
	"phonesSold":"270 millones, Año 2020",
	"phones":[
		{
			"id":"p0",
			"title":"Samsung Galaxy A52",
			"price":"349,90 €",
			"releaseDate":"17-03-2021",
			"size":"159,9 x 75,1 x 8,4 mm",
			"resolution":"2.400 x 1.080 px"
		},
	]
}

```
