package ec.edu.uce.planTitulacion.ejb.schedule.impl;

import ec.edu.uce.planTitulacion.ejb.impl.PlanDaoImpl;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class ServicioEmail {

//    @EJB
    private PlanDaoImpl plan = new PlanDaoImpl();

//    @Schedule(second = "*/30", minute = "*", hour = "*")
//    @Schedule(second = "0", minute = "0", hour = "8")
    @Schedule(second = "0", minute = "*", hour = "*")
    private void BarrerPlan() throws Exception{
        System.out.println("-------------empieza el servicio------------");
//        plan = new PlanDaoImpl();
        plan.BarrerPlan();
    }
    
//    @Schedule(second = "*", minute = "*", hour = "*")
//    private void saludo(){
//        System.out.println("-----------Prueba de Schedule----------");
//    }
}
