import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BaseRestService} from './base-rest.service';
import {Cliente} from '../models/cliente.model';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends BaseRestService {
  constructor(protected httpClient: HttpClient) {
    super(httpClient);
    this.baseURL = environment.apiUrl.cliente;
    this.endpoint = 'clientes';
  }

  getAllClientes(): Observable<object> {
    return this.get();
  }

  getClienteById(clienteId: number): Observable<object> {
    return this.get(clienteId + '');
  }

  createCliente(cliente: Cliente): Observable<object> {
    return this.post('', cliente);
  }

  updateCliente(cliente: Cliente): Observable<object> {
    return this.put('', cliente);
  }

  deleteClienteById(clienteId: number): Observable<object> {
    return this.delete('/' + clienteId);
  }
}
