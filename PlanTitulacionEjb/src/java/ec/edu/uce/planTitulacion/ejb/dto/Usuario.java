package ec.edu.uce.planTitulacion.ejb.dto;

public class Usuario {
    private Integer usrId;
    private String nombre;
    private String email;
    private String usrPassword;
    private String usrNick;
    private String usrIdentificacion;

    public String getCedula() {
        return usrIdentificacion;
    }

    public void setCedula(String cedula) {
        this.usrIdentificacion = cedula;
    }
    
    public Integer getIdUsuario() {
        return usrId;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.usrId = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return usrPassword;
    }

    public void setPassword(String password) {
        this.usrPassword = password;
    }

    public String getNick() {
        return usrNick;
    }

    public void setNick(String nick) {
        this.usrNick = nick;
    }
}
