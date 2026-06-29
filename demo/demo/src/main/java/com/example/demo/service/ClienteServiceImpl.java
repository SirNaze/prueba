package com.example.demo.service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // ── Listar ──────────────────────────────────────────────
    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Registrar (Spring Data JPA) ─────────────────────────
    @Override
    public ClienteDTO registrarCliente(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return toDTO(guardado);
    }

    // ── Actualizar (Spring Data JPA) ────────────────────────
    @Override
    public ClienteDTO actualizarCliente(Integer id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setApellidoMaterno(dto.getApellidoMaterno());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setSexo(dto.getSexo());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCorreoElectronico(dto.getCorreoElectronico());

        Cliente actualizado = clienteRepository.save(cliente);
        return toDTO(actualizado);
    }

    // ── Eliminar (Stored Procedure) ─────────────────────────
    @Transactional
    @Override
    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cliente no encontrado con ID: " + id);
        }
        clienteRepository.eliminarCliente(id);
    }

    // ── Mappers ─────────────────────────────────────────────
    private ClienteDTO toDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getIdCliente());
        dto.setNombres(c.getNombres());
        dto.setApellidoPaterno(c.getApellidoPaterno());
        dto.setApellidoMaterno(c.getApellidoMaterno());
        dto.setFechaNacimiento(c.getFechaNacimiento());
        dto.setSexo(c.getSexo());
        dto.setDireccion(c.getDireccion());
        dto.setCorreoElectronico(c.getCorreoElectronico());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setNombres(dto.getNombres());
        c.setApellidoPaterno(dto.getApellidoPaterno());
        c.setApellidoMaterno(dto.getApellidoMaterno());
        c.setFechaNacimiento(dto.getFechaNacimiento());
        c.setSexo(dto.getSexo());
        c.setDireccion(dto.getDireccion());
        c.setCorreoElectronico(dto.getCorreoElectronico());
        return c;
    }
}