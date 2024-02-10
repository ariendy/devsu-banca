package org.example.clientemicroservicio.service;

import org.example.clientemicroservicio.dtos.ClienteDTO;
import org.example.clientemicroservicio.exception.NotFoundException;
import org.example.clientemicroservicio.exception.ServiceException;
import org.example.clientemicroservicio.model.Cliente;
import org.example.clientemicroservicio.repository.ClienteDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ClienteService {

    private final ClienteDao clienteDao;

    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> getAllUsuarios() {
        return clienteDao.findAll().stream().map(ClienteDTO::fromCliente).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO getClienteById(Long id) {
        return clienteDao.findById(id)
                .map(ClienteDTO::fromCliente)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        validateCliente(clienteDTO);
        Cliente cliente = clienteDTO.toCliente();
        cliente = clienteDao.save(cliente);
        return ClienteDTO.fromCliente(cliente);
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        validateCliente(clienteDTO);
        Cliente cliente = clienteDTO.toCliente();
        cliente = clienteDao.save(cliente);
        return ClienteDTO.fromCliente(cliente);
    }

    public void deleteCliente(Long id) {
        clienteDao.deleteById(id);
    }

    private void validateCliente(ClienteDTO clienteDTO) {
        Objects.requireNonNull(clienteDTO);

        if(clienteDTO.getIdentificacion() == null || "".equals(clienteDTO.getIdentificacion())) {
            throw new ServiceException("La identificación no pude estar en blanco.");
        }

        if(clienteDTO.getNombre() == null || "".equals(clienteDTO.getNombre())) {
            throw new ServiceException("El nombre no pude estar en blanco.");
        }

        clienteDao.findFirstByIdentificacion(clienteDTO.getIdentificacion()).ifPresent(u -> {
            if(clienteDTO.getPersonaId() == null || !Objects.equals(u.getPersonaId(), clienteDTO.getPersonaId())) {
                throw new ServiceException("Esta identificación ya esta registrado.");
            }
        });
    }
}
