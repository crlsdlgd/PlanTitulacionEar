package ec.edu.uce.planTitulacion.jsf.managedBean.sesion;

import ec.edu.uce.planTitulacion.ejb.constantes.RolConstantes;
import ec.edu.uce.planTitulacion.ejb.dao.RolDao;
import ec.edu.uce.planTitulacion.ejb.dto.Rol;
import static ec.edu.uce.planTitulacion.jsf.managedBeans.ControladorUsuario.user;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="sesionUsuarioBean")
@SessionScoped
public class SesionUsuarioBean implements Serializable{
    
    private static final long serialVersionUID = 2300187948955776410L;
    
    private boolean estudiante, docente, consejo;
    List<Rol> listaRolUsuario;

//    @EJB
//    private RolDao rolDao;

//    public SesionUsuarioBean() throws Exception {
//        System.out.println("estro a sesion");
//        this.listaRolUsuario=rolDao.buscarRolByUser(user);
//        estudiante=docente=consejo=false;
//        for(int i=0;i<listaRolUsuario.size();i++){
//            if(listaRolUsuario.get(i).getIdRol()==RolConstantes.ROL_BD_ESTUDIANTE_VALUE){
//                estudiante=true;
//            }else if(listaRolUsuario.get(i).getIdRol()==RolConstantes.ROL_BD_DOCENTE_VALUE){
//                docente=true;
//            }else if(listaRolUsuario.get(i).getIdRol()==RolConstantes.ROL_BD_CONSEJO_VALUE){
//                consejo=true;
//            }
//        }
//    }
    
    
    
    public boolean getEstudiante() {
//        estudiante=false;
//        for(int i=0;i<listaRolUsuario.size();i++){
//            if(listaRolUsuario.get(i).getIdRol()==RolConstantes.ROL_BD_ESTUDIANTE_VALUE){
//                estudiante=true;
//            }
//        }
        return estudiante;
    }

    public void setEstudiante(boolean estudiante) {
        this.estudiante = estudiante;
    }

    public boolean getDocente() {
        return docente;
    }

    public void setDocente(boolean docente) {
        this.docente = docente;
    }

    public boolean getConsejo() {
        return consejo;
    }

    public void setConsejo(boolean consejo) {
        this.consejo = consejo;
    }
    
    
}
