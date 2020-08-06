package ar.com.ada.api.hoteltresvagos.models.request;

import java.math.BigDecimal;
import java.sql.Date;

public class ReservaRequest {

    public boolean isOk = false;
    public int huespedId;
    public Date fechaIngreso;
    public Date fechaEgreso;
    public BigDecimal importePagado;
    public String message = "";
    
}