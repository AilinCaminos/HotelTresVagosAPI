package ar.com.ada.api.hoteltresvagos.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.hoteltresvagos.entities.Huesped;
import ar.com.ada.api.hoteltresvagos.entities.Reserva;
import ar.com.ada.api.hoteltresvagos.repositories.ReservarRepository;

@Service
public class ReservaService {

    @Autowired
    ReservarRepository reservaRepo;
    @Autowired
    HuespedService huespedService;

    public void grabar(Reserva reserva) {

        huespedService.grabar(reserva.getHuesped());

    }

    public Reserva buscarPorId(int reservaId) {

        Optional<Reserva> b = reservaRepo.findById(reservaId);

        if (b.isPresent())
            return b.get();

        return null;
    }

    public boolean baja(int reservaId) {

        Reserva reserva = buscarPorId(reservaId);

        if (reserva == null)
            return false;

        return baja(reserva);
    }

    public boolean baja(Reserva reserva) {

        reservaRepo.delete(reserva);
        return true;
    }

    public List<Reserva> getReservas() {

        return reservaRepo.findAll();
    }

    public List<Reserva> buscarReservasPorNombre(String nombre) {

        return reservaRepo.findByNombreHuesped(nombre);
    }

    public void crearReserva(Reserva reserva) {

        grabar(reserva);

    }

    public Reserva crearReserva(int huespedId, Date fechaIngreso, Date fechaEgreso, BigDecimal importePagado) {

        Reserva reserva = new Reserva();

        reserva.setFechaReserva(new Date());
        reserva.setFechaIngreso(fechaIngreso);
        reserva.setFechaEgreso(fechaEgreso);
        reserva.setImporteReserva(new BigDecimal(500));
        reserva.setImporteTotal(new BigDecimal(10000));
        reserva.setImportePagado(importePagado);
        reserva.setImporteAdeudado(reserva.getImporteTotal().subtract(reserva.getImportePagado()));

        if (reserva.getImporteAdeudado().doubleValue() == 0) {

            reserva.setTipoEstadoId(10);

        } else {

            reserva.setTipoEstadoId(20);

        }

        Huesped huesped = huespedService.buscarPorId(huespedId);
        huesped.agregarReserva(reserva);

        crearReserva(reserva);

        return reserva;

    }

    public List<Reserva> listarReservas(){

        return reservaRepo.findAll();

    }


}