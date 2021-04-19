package ec.edu.uce.planTitulacion.jsf.validadores;

import ec.edu.uce.planTitulacion.ejb.constantes.ConstantesSistema;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validarCarnetDiscapacidad")
public class ValidarCarnetDiscapacidad implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent comp, Object valor) throws ValidatorException {
        String strValor = (String) valor;
        Integer discapacidad = (Integer) comp.getAttributes().get("discapacidad");
        if (discapacidad == ConstantesSistema.SI_DISCAPACIDAD_VALUE) {
            if ("".equals(strValor) || strValor == null) {
                FacesMessage msg = new FacesMessage("Valor inválido");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//                fc.addMessage(comp.getClientId(fc), msg);
                throw new ValidatorException(msg);
            }
        }
    }

}
