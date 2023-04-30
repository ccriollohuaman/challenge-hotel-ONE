# Directorio de Reservar para un hotel | Challenge | BackEnd

<p align="center" >
     <img width="300" heigth="300" src="https://user-images.githubusercontent.com/91544872/189419040-c093db78-c970-4960-8aca-ffcc11f7ffaf.png">
</p>

## Descripción
Challenge de Alura Latam - Programa ONE donde se crea un aplicación para la gestión de reservaciones de un hotel.

- Tiene autenticación de usuario y contraseña para ingresar al sistema
- Permite registrar las fechas de check in y check out, método de pago y genera un id de reserva
- Permite registrar los datos del cliente asociando el id de reserva creado en el punto anterior
- Se puede ver todos los registros, buscar por id de reserva o apellido de cliente y editar o eliminar reservar y huéspedes
- Se tiene dos perfiles de usuario: Administrador y Agente
- El Administrador puede crear nuevos usuarios de perfil "Administrador" y "Agente", registrar, buscar, editar o eliminar reservas
- El Agente solo puede registrar, buscar, editar o eliminar reservas

## Explicación del Desarrollo

### Base de Datos

- Se trabaja con una base de datos denominada "hotel_alura" gestionada por MySQL, donde se tiene tres tablas: "usuario", "reserva" y "huesped", estás dos últimas están relacionadas por el id de la tabla reserva.
- Al crear la tabla usuario también se inserta un usuario de tipo administrador que sería el que permitirá ingresar a la aplicación y crear nuevos usuarios.

1. Tabla Usuario

```sql
CREATE TABLE usuario(
  id INT AUTO_INCREMENT,
  dni VARCHAR(10) NOT NULL UNIQUE,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  usuario VARCHAR(50) NOT NULL UNIQUE,
  contrasena VARCHAR(150) NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB;
```

2. Tabla Reserva

```sql
CREATE TABLE reserva(
id INT AUTO_INCREMENT,
fecha_entrada DATE,
fecha_salida DATE,
valor DECIMAL NOT NULL DEFAULT 0,
forma_pago VARCHAR(50),
PRIMARY KEY(id)
)ENGINE=InnoDB, AUTO_INCREMENT=100;
```

3. Tabla Huesped

```sql
CREATE TABLE huesped(
id INT AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
fecha_nacimiento DATE,
nacionalidad VARCHAR(50) NOT NULL,
telefono VARCHAR(20) NOT NULL,
reserva_id INT,
PRIMARY KEY(ID),
FOREIGN KEY(reserva_id) REFERENCES reserva(id)
)ENGINE=InnoDB;
```

### Ventanas

- A nivel visual se cuenta con 9 ventanas.
- Las ventanas MenuPrincipal, Login, MenuUsuario, RegistroReserva, RegistroHuesped, Exito y Busqueda fueron obtenidas desde el [Código base](https://github.com/alura-challenges/challenge-one-alura-hotel-latam) generado por Alura

1. MenuPrincipal: Es la ventana que se muestra al ejecutarse la aplicación, tiene un botón que redigire a la ventana Login.
2. Login: Ventana que tiene un autenticador por usuario y contraseña y abre la ventana MenuUsuario.
3. MenuUsuario: En esta ventana si se auntentica con un usuario de tipo Administrador muestra tres botones "Registro de reservas", "Búsqueda" y "Administrador" pero si lo hace con un usuario de tipo Agente el botón "Administrador" no aparecerá.
4. RegistroReserva: Permite registrar la fecha de check in,la fecha de check out, método de pago y genera el valor de reserva de acuerdo con la cantidad de días entre las fechas seleccionadas. ($25 por día)
5. ConfirmacionFechasReserva: Esta ventana aparece luego que el cliente registra las fechas, método de pago y se genera el valor. Es de tipo Dialogo, muestra la información seleccionada y consulta al cliente si quiere "Continuar" o "Regresar", si continua va a la ventana RegistroHuesped y si regresa se queda en la ventana RegistroReserva
6. RegistroHuesped: En esta ventana se tiene un formulario para guardar los datos del cliente que generó la reserva, luego de guardar los datos aparece la ventana Exito.
7. Exito: Es de tipo Dialogo y aparece si se guardó correctamente los datos y redirige a la ventana MenuPrincipal.
8. Busqueda: Esta ventana permite buscar, editar o eliminar los registros.
9. Administrador: Se muestra un formulario para crear nuevos usuarios.

### Lógica

- Se desarrolló utilizando JDBC y siguiendo la estructura vista, modelo, controlador, DAO, common y factory.

1. En el paquete factory se hace la conexión usando ComboPooledDataSource para mejorar el rendimiento y escalabilidad de la aplicación ya que se proporciona un conjunto de conexiones de bases de datos.

- Se usa las [Librerías externas](#librerías-externas-utilizadas) c3p0 y mchange-commons.

```java
public class ConnectionFactory {
	
	public DataSource datasource;
	
	public ConnectionFactory() {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("root");
		
		this.datasource = pooledDataSource;	
	}
	
	public Connection recuperaConexion() {
		try {
			return this.datasource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
```

2. En el paquete modelo se tiene los objetos que replican la estructura de las tablas creadas en la base de datos: Usuario, Reserva y Huesped.

- En Usuario se creó una función especial que encripta las contraseñas con SHA-256

```java
MessageDigest digest = MessageDigest.getInstance("SHA-256");
```

3. En el paquete dao se hace las peticiones a la base de datos

- UsuarioDAO: Permite realiar INSERT de usuarios nuevos en la tabla usuario de la BD hotel_alura.
- LoginDAO: Realiza SELECT de usuarios de la tabla usuario de la BD hotel_alura para validar si el usuario que está haciendo login existe o no.
- ReservaDAO: Permite realizar INSERT de nuevas reservas hechas en RegistroReserva a la tabla reserva de la BD hotel_alura, SELECT de reservas para enlistarlas en Búsqueda, UPDATE y DELETE.
-HuespedDAO: Realiza INSERT de nuevos huespedes registrados en RegistroHuesped a la tabla huesped de la BD hotel_alura, SELECT de huespedes para enlistarlos en Búsqueda, UPDATE y DELETE.

4. En el paquete controller manejamos la lógica para realizar las operaciones entre las ventanas y las clases DAO

- ReservaController: Aquí se tiene la regla para calcular el valor de la reserva de acuerdo a las fechas seleccionadas por el cliente. Por defecto el valor de la reserva por día es $25.99.

```java
public String calcularValorReserva(Date fechaEntrada, Date fechaSalida) {
  Float valorDiario = (float)25.99;
	long diferenciaFechas = fechaSalida.getTime() - fechaEntrada.getTime();
	Integer diferenciaDias = (int) (diferenciaFechas / (24 * 60 * 60 * 1000));
	Float valorReserva = diferenciaDias * valorDiario;
	return String.valueOf(valorReserva);
}
```

- LoginController: Aquí se maneja las operaciones para cuando el cliente ingresa su usuario y contraseña luego de enviar la información al DAO se sobreescribe con Arrays.fill lo guardado en la variable char[] para aumentar la complejidad de recuperar la contraseña ingresada para terceros.

```java
public Usuario autentica(String usuario, char[] contrasena) {
	Usuario resultado = loginDAO.autentica(usuario, contrasena);
	Arrays.fill(contrasena, '\0');
	return resultado;
}
```

5. En el paquete common se tiene subpaquetes con funciones comunes para el programa

- El subpaquete vstatic tiene la clase Sistema que guarda variables estáticas, ahora solo cuenta con una que sirve para almacenar el tipo de usuario autenticado en Login que permite al cliente navegar por la aplicación mostrando botones que le corresponden a su tipo.

```java
public class Sistema {

    public static Usuario usuarioAutenticado;
	
}
```
### Video demostrativo



## Librerías externas utilizadas
* [jcalendar-1.4.jar](https://toedter.com/jcalendar/)
* [jgoodies-common-1.2.0.jar](https://www.jgoodies.com/downloads/libraries/)
* [mysql-connector-j-8.0.32.jar](https://dev.mysql.com/downloads/connector/j/)
* [c3p0-0.9.5.5.jar](https://sourceforge.net/projects/c3p0/)
* [mchange-commons-java-0.2.20.jar](https://mvnrepository.com/artifact/com.mchange/mchange-commons-java/0.2.20)


## Herramientas utilizadas
* [Eclipse](https://www.eclipse.org/downloads/)
* [MySQL](https://dev.mysql.com/downloads/workbench/)

## Lenguaje utilizado 
* [Java 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)

## Desarrollador
[<img src="https://avatars.githubusercontent.com/u/83378496?v=4" width=90><br><sub>Cristhian Criollo</sub>](https://github.com/ccriollohuaman)
