package ar.com.ada.api.hoteltresvagos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.hoteltresvagos.entities.Reserva;
import ar.com.ada.api.hoteltresvagos.models.request.ReservaRequest;
import ar.com.ada.api.hoteltresvagos.models.response.GenericResponse;
import ar.com.ada.api.hoteltresvagos.services.ReservaService;

@RestController
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @PostMapping("/reservas")
    public ResponseEntity<GenericResponse> postReserva(@RequestBody ReservaRequest reserva){

        GenericResponse resp = new GenericResponse();

        Reserva r = reservaService.crearReserva(reserva.huespedId, reserva.fechaIngreso, reserva.fechaEgreso, reserva.importePagado);

        resp.isOk = true;
        resp.message = "Se creo la reserva con exito";
        resp.id = r.getReservaId();

        return ResponseEntity.ok(resp);

    }

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> getReservas(@RequestParam(value = "nombre", required = false) String nombre){

        List<Reserva> lr;

        if (nombre == null) {
            lr = reservaService.getReservas();
        } else {
            lr = reservaService.buscarReservasPorNombre(nombre);
        }

        return ResponseEntity.ok(lr);

    }

    @GetMapping("reservas/{reservaId}")
    public ResponseEntity<Reserva> getReservaPorId(@PathVariable int reservaId){

        Reserva r = reservaService.buscarPorId(reservaId);

        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(r);

    }

    @PutMapping("/reservas/{reservaId}")
    public ResponseEntity<?> putReserva(@PathVariable int reservaId, @RequestBody Reserva reserva) {

        GenericResponse r = new GenericResponse();

        Reserva original = reservaService.buscarPorId(reservaId);

        if (original == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean resultado = false;
        resultado = reservaService.actualizarReserva(original, reserva);

        if (resultado) {
            r.isOk = true;
            r.id = original.getReservaId();
            r.message = "Reserva actualizada con éxito.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo actualizar el huesped.";

            return ResponseEntity.badRequest().body(r);
        }

    }
    
}