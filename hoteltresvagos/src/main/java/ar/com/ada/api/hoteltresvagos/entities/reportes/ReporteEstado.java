package ar.com.ada.api.hoteltresvagos.entities.reportes;

import java.math.BigDecimal;

import javax.persistence.Column;

public interface ReporteEstado {

    Integer getEstadoId();

    Integer getCantidadReservas();

    BigDecimal getTotalImporteReserva();

    BigDecimal getTotalImportePagado();

    BigDecimal getImporteTotal();

    String getDescripcion();

}