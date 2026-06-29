package com.example.demo.service;
import com.example.demo.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarClientes();
    ClienteDTO registrarCliente(ClienteDTO dto);
    ClienteDTO actualizarCliente(Integer id, ClienteDTO dto);
    void eliminarCliente(Integer id);
}