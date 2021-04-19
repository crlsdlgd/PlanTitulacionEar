package ec.edu.uce.planTitulacion.ejb.utilities;

import java.sql.Date;

public class fechaUtilToSql {
    public static Date fechaUtilToSql(java.util.Date fecha) {
        Date fechaSql= new Date(fecha.getYear(),fecha.getMonth(),fecha.getDate());
        return fechaSql;
    }
}
