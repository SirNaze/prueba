package com.example.demo.controller;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody ClienteDTO dto) {
        ClienteDTO registrado = clienteService.registrarCliente(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Cliente registrado con éxito.");
        response.put("cliente", registrado);
        return ResponseEntity.ok(response);
    }

    // PUT /api/clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Integer id,
            @RequestBody ClienteDTO dto) {
        ClienteDTO actualizado = clienteService.actualizarCliente(id, dto);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Cliente actualizado con éxito.");
        response.put("cliente", actualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            clienteService.eliminarCliente(id);
            response.put("mensaje", "Cliente eliminado con éxito.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Captura el "El cliente con ID X no existe" que lanzamos desde el Service
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Ataja cualquier otro imprevisto de la base de datos
            response.put("error", "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}