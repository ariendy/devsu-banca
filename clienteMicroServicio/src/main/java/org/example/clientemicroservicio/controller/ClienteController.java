package org.example.clientemicroservicio.controller;

import org.example.clientemicroservicio.dtos.ClienteDTO;
import org.example.clientemicroservicio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> getAllClientes() {
        return clienteService.getAllUsuarios();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO getClienteById(@PathVariable  Long id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO saveCliente(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.saveCliente(clienteDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO updateCliente(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.updateCliente(clienteDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }
}
