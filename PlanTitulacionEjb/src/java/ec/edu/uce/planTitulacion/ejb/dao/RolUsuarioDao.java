package ec.edu.uce.planTitulacion.ejb.dao;

import ec.edu.uce.planTitulacion.ejb.dto.Plan;
import ec.edu.uce.planTitulacion.ejb.dto.RolUsuario;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RolUsuarioDao {

    public List<RolUsuario> listarIntegrantesByPlan(Plan plan) throws Exception;
    
}
