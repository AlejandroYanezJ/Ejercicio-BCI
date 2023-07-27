package cl.prueba.ejerciciobci.constants;

public class Constants {

    //validacion token
    public static final String INVALID_TOKEN ="Token inválido";

    //validacion entrada
    public static final String EMPTY_NAME="name no puede ser vacío/nulo";
    public static final String EMPTY_EMAIL="email no puede ser vacío/nulo";
    public static final String EMPTY_PSW="password no puede ser vacía/nulo";
    public static final String EMPTY_PHONES_NUMBER="number no puede ser vacío/nulo";
    public static final String EMPTY_PHONES_CITYCODE="citycode no puede ser vacío/nulo";;
    public static final String EMPTY_PHONES_CONTRYCODE="contrycode no puede ser vacío/nulo";
    public static final String INVALID_PSW = "Contraseña inválida, validar las reglas para contraseña";
    public static final String INVALID_EMAIL = "Email inválido, validar las reglas para email";
    public static final String INVALID_UUID = "Id invalido, por favor corroborar el id del usuario";

    //validaciones usuario
    public static final String EMAIL_EXIST = "El email ya se encuentra registrado";
    public static final String USER_PSW_EMAIL_ERROR = "Email y/o contraseña incorrectos";
    public static final String USER_INACTIVE= "El usuario no se encuentra activo";
    public static final String USER_NOT_FOUND_BY_ID="No existe un usuario para el id ingresado";

    //ok

    public static final String USER_DELETE_SUCCESS = "EL usuario ha sido eliminado correctamente";

    //Errores
    public static final String NULL_POINTER_EX= "NullPointerException";
    public static final String USR_INSERT_ERROR = "Ha ocurrido un error almacenando el usuario";
    public static final String USR_DELETE_ERROR = "Ha ocurrido un error borrando el usuario";
    public static final String USR_UPDATE_ERROR = "Ha ocurrido un error actualizando el usuario";
    public static final String PHONES_GET_ERROR = "Ha ocurrido un error obteniendo los telefonos del usuario";
    public static final String PHONES_DELETE_ERROR = "Ha ocurrido un error eliminando los telefonos del usuario";
    public static final String PHONES_INSERT_ERROR = "Ha ocurrido un error almacenando los telefonos del usuario";
    public static final String GENERIC_ERROR = "Ha ocurrido un error interno en la aplicacion";

}
