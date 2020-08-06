package ar.com.ada.api.hoteltresvagos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<GenericResponse> crearReserva(@RequestBody ReservaRequest reserva){

        GenericResponse resp = new GenericResponse();

        Reserva r = reservaService.crearReserva(reserva.huespedId, reserva.fechaIngreso, reserva.fechaEgreso, reserva.importePagado);

        resp.isOk = true;
        resp.message = "Se creo la reserva con exito";
        resp.id = r.getReservaId();

        return ResponseEntity.ok(resp);

    }

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> listarReservas(){

        return ResponseEntity.ok(reservaService.listarReservas());

    }

    @GetMapping("reservas/{reservaId}")
    public ResponseEntity<Reserva> traerReserva(@PathVariable int reservaId){

        Reserva r = reservaService.buscarPorId(reservaId);

        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(r);

    }
    
}