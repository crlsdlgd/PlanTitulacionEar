package ec.edu.uce.planTitulacion.jsf.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validarCorreo")
public class ValidarCorreo implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent comp, Object valor) throws ValidatorException {
        String correo = (String) valor;
        String aux = "";
        boolean flag = false;
        if (correo.length() > 11) {
            for (int i = correo.length() - 11; i < correo.length(); i++) {
                aux = aux + correo.charAt(i);
            }
        } else {
            FacesMessage msg = new FacesMessage("El correo institucional UCE cuenta con al menos 11 caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (aux.equals("@uce.edu.ec")) {
            flag = true;
        }
        if(!flag){
            FacesMessage msg = new FacesMessage("Su correo debe ser Institucional UCE");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
