package ec.edu.uce.planTitulacion.ejb.impl;

import static ec.edu.uce.planTitulacion.ejb.constantes.ConstantesSistema.TIPO_IDENTIFICACION_CEDULA_VALUE;
import static ec.edu.uce.planTitulacion.ejb.constantes.RolConstantes.ROL_BD_ESTUDIANTE_LABEL;
import static ec.edu.uce.planTitulacion.ejb.constantes.RolConstantes.ROL_BD_ESTUDIANTE_VALUE;
import ec.edu.uce.planTitulacion.ejb.dao.UsuarioDao;
import ec.edu.uce.planTitulacion.ejb.dto.Persona;
import ec.edu.uce.planTitulacion.ejb.dto.Plan;
import ec.edu.uce.planTitulacion.ejb.dto.Usuario;
import ec.edu.uce.planTitulacion.ejb.jdbc.impl.DAO;
import static ec.edu.uce.planTitulacion.ejb.utilities.fechaUtilToSql.fechaUtilToSql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class UsuarioDaoImpl extends DAO implements UsuarioDao {

    @Override
    public boolean registrar(Usuario usuario) throws Exception {

        String nick = sacarNick(usuario.getUsrPersona().getPrsMailInstitucional());
        Date prsfechaNacimientoSql = fechaUtilToSql(usuario.getUsrPersona().getPrsFechaNacimiento());
        boolean flag = false;
        try {
            this.Conectar();
            this.getCn().setAutoCommit(false);
            PreparedStatement st = this.getCn().prepareStatement("INSERT INTO persona (prs_identificacion, prs_nombres, prs_primer_apellido, prs_segundo_apellido,prs_mail_institucional, prs_mail_personal, prs_telefono, prs_carnet_conadis, prs_tipo_identificacion, prs_sexo, prs_discapacidad, prs_porcentaje_discapacidad, etn_id, prs_fecha_nacimiento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, usuario.getUsrPersona().getPrsIdentificacion());
            st.setString(2, usuario.getUsrPersona().getPrsNombres());
            st.setString(3, usuario.getUsrPersona().getPrsPrimerApellido());
            st.setString(4, usuario.getUsrPersona().getPrsSegundoApellido());
            st.setString(5, usuario.getUsrPersona().getPrsMailInstitucional());
            st.setString(6, usuario.getUsrPersona().getPrsMailPersonal());
            st.setString(7, usuario.getUsrPersona().getPrsTelefono());
            st.setString(8, usuario.getUsrPersona().getPrsCarnetConadis());
            st.setInt(9, usuario.getUsrPersona().getPrsTipoIdentificacion());
            st.setInt(10, usuario.getUsrPersona().getPrsSexo());
            st.setInt(11, usuario.getUsrPersona().getPrsDiscapacidad());
            st.setInt(12, usuario.getUsrPersona().getPrsPorcentajeDiscapacidad());
            st.setInt(13, usuario.getUsrPersona().getPrsEtnia().getEtnId());
            st.setDate(14, prsfechaNacimientoSql);
            st.executeUpdate();
            st.close();

            PreparedStatement st2 = this.getCn().prepareStatement("SELECT MAX(prs_id) AS prs_id FROM persona");
            ResultSet rs;
            int prsId = 0;
            rs = st2.executeQuery();
            while (rs.next()) {
                prsId = rs.getInt("prs_id");
            }
            rs.close();
            st2.close();

            PreparedStatement st3 = this.getCn().prepareStatement("INSERT INTO usuario (usr_nick, usr_password, prs_id) VALUES(?,?,?)");
            st3.setString(1, nick);
            st3.setString(2, usuario.getUsrPassword());
            st3.setInt(3, prsId);
            st3.executeUpdate();
            st3.close();

            PreparedStatement st4 = this.getCn().prepareStatement("SELECT MAX(usr_id) AS usr_id FROM usuario");
            ResultSet rs2;
            int usrId = 0;
            rs2 = st4.executeQuery();
            while (rs2.next()) {
                usrId = rs2.getInt("usr_id");
            }
            rs2.close();
            st4.close();

            PreparedStatement st5 = this.getCn().prepareStatement("INSERT INTO usuario_rol (rol_id, usr_id) VALUES(" + ROL_BD_ESTUDIANTE_VALUE + ",?)");
            st5.setInt(1, usrId);
            st5.executeUpdate();
            st5.close();
            this.getCn().commit();
            flag = true;

        } catch (Exception e) {
            this.getCn().rollback();
            return false;

        } finally {
            this.Cerrar();
        }

        return flag;
    }

    @Override
    public List<Usuario> listarUsuario() throws Exception {
        List<Usuario> lista;
        ResultSet rs;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT u.usr_id, u.prs_id, pe.prs_primer_apellido, pe.prs_nombres, pe.prs_mail_institucional, u.usr_nick FROM usuario u, persona pe");
            rs = st.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                Persona person = new Persona();
                usuario.setUsrId(rs.getInt("usr_id"));
                person.setPrsId(rs.getInt("prs_id"));
                person.setPrsPrimerApellido(rs.getString("prs_primer_apellido"));
                person.setPrsNombres(rs.getString("prs_nombres"));
                person.setPrsMailInstitucional(rs.getString("prs_mail_institucional"));
                usuario.setUsrPersona(person);
                usuario.setUsrNick(rs.getString("usr_nick"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public List<Usuario> listarUserByPlan(Plan plan) throws Exception {
        List<Usuario> lista;
        ResultSet rs;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT u.usr_id, u.prs_id, pe.prs_primer_apellido, pe.prs_nombres, pe.prs_mail_institucional, u.usr_nick \n"
                    + "FROM usuario u, persona pe \n"
                    + "WHERE u.prs_id=pe.prs_id \n"
                    + "AND usr_nick = ? \n"
                    + "AND usr_password = ? ");

            st.setInt(1, plan.getPlnId());
            rs = st.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                Persona person = new Persona();
                usuario.setUsrId(rs.getInt("usr_id"));
                person.setPrsId(rs.getInt("prs_id"));
                person.setPrsPrimerApellido(rs.getString("prs_primer_apellido"));
                person.setPrsNombres(rs.getString("prs_nombres"));
                person.setPrsMailInstitucional(rs.getString("prs_mail_institucional"));
                usuario.setUsrPersona(person);
                usuario.setUsrNick(rs.getString("usr_nick"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

    @Override
    public Usuario buscarUsuarioLogin(String nick, String pass) throws Exception {
        Usuario usuario;
        ResultSet rs;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT u.usr_id, u.prs_id, pe.prs_primer_apellido, pe.prs_nombres, pe.prs_mail_institucional, u.usr_nick \n"
                    + "FROM usuario u, persona pe \n"
                    + "WHERE u.prs_id=pe.prs_id \n"
                    + "AND usr_nick = ? \n"
                    + "AND usr_password = ? ");
            st.setString(1, nick);
            st.setString(2, pass);
            rs = st.executeQuery();
            usuario = new Usuario();

            while (rs.next()) {
                usuario.setUsrId(rs.getInt("usr_id"));
                Persona person = new Persona();
                person.setPrsId(rs.getInt("prs_id"));
                person.setPrsPrimerApellido(rs.getString("prs_primer_apellido"));
                person.setPrsNombres(rs.getString("prs_nombres"));
                person.setPrsMailInstitucional(rs.getString("prs_mail_institucional"));
                usuario.setUsrPersona(person);
                usuario.setUsrNick(rs.getString("usr_nick"));
            }

            if (usuario.getUsrId() == null) {
                return null;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return usuario;

    }

    @Override
    public List<String> autoCompletarEstudiante(String query) throws Exception {
        List<String> lista;
        ResultSet rs;
        query = "%" + query + "%";
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT pe.prs_nombres\n"
                    + "FROM usuario u, rol r, rol_usuario ru, persona pe\n"
                    + "WHERE UPPER(pe.prs_primer_apellido) LIKE UPPER( ? ) AND\n"
                    + "u.usr_id=ru.usr_id AND\n"
                    + "ru.rol_id = r.rol_id AND\n"
                    + "pe.prs_id=u.prs_id AND\n"
                    + "r.rol_descripcion='" + ROL_BD_ESTUDIANTE_LABEL + "'");
            st.setString(1, query);
            rs = st.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                String aux = rs.getString("prs_nombres");
                lista.add(aux);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;

    }

    @Override
    public boolean existeEstudiante(String txtEstudiante) throws Exception {
        ResultSet rs;
        List<Usuario> lista;
        boolean flag = true;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT u.usr_id FROM usuario u, usuario_rol ru, rol r\n"
                    + "WHERE ru.usr_id=u.usr_id AND\n"
                    + "ru.rol_id = r.rol_id AND\n"
                    + "pe.prs_id = u.prs_id AND\n"
                    + "r.rol_descripcion = 'Estudiante' AND\n"
                    + "pe.prs_nombres= ? ");
            st.setString(1, txtEstudiante);
            rs = st.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsrId(rs.getInt("usr_id"));
                lista.add(u);
            }
            if (lista.isEmpty()) {
                flag = false;
            }
        } catch (Exception e) {
            return false;

        } finally {
            this.Cerrar();
        }
        return flag;
    }

    @Override
    public boolean existeIdentificacion(String prsIdentificacion) {
        boolean flag = false;
        ResultSet rs;
        int usr_id=-1;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT prs_id FROM persona WHERE prs_identificacion = ?");
            st.setString(1, prsIdentificacion);
            rs = st.executeQuery();
            while(rs.next()){
                usr_id=(rs.getInt("prs_id"));
            }
            if(usr_id==-1){
                flag=true;
            }
        } catch (Exception e) {

        }
        return flag;
    }

    @Override
    public Usuario buscarUsuarioPrecursor(Plan plan) throws Exception {
        Usuario user = new Usuario();
        Persona person = new Persona();
        ResultSet rs;
        try {
            this.Conectar();
            PreparedStatement st = this.getCn().prepareCall("SELECT u.usr_id, pe.prs_nombres FROM usuario u, persona pe WHERE u.prs_id=pe.prs_id AND u.usr_id= ?");
            st.setInt(1, plan.getPlnPropuestoPor());
            rs = st.executeQuery();
            while (rs.next()) {
                user.setUsrId(rs.getInt("usr_id"));
                person.setPrsNombres(rs.getString("prs_nombres"));
                user.setUsrPersona(person);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return user;

    }

    private String sacarNick(String correo) {
        String aux = "";
        if (correo.length() > 11) {
            for (int i = 0; i < correo.length() - 11; i++) {
                aux = aux + correo.charAt(i);
            }
        }
        return aux;
    }

}
