import {Component, OnInit} from '@angular/core';
import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../../models/cliente.model';
import {of} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';

@Component({
  selector: 'app-cliente-view',
  templateUrl: './cliente-view.component.html',
  styleUrls: ['./cliente-view.component.scss']
})
export class ClienteViewComponent implements OnInit {

  clientToSearch = '';
  displayedClientes: Cliente[];
  clientes: Cliente[];

  constructor(public clienteService: ClienteService) {
  }

  ngOnInit(): void {
    this.clienteService.getAllClientes().subscribe(res => {
      this.clientes = res as Cliente[];
      this.displayedClientes = res as Cliente[];
    });
  }

  onSearchValue(event: any): void {
    of(event.target.value).pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map((term: string) => {
        if (!term || !term.length) {
          return this.clientes;
        } else {
          const res = this.clientes
            .filter(v => v.nombre.toLowerCase().indexOf(term.toLowerCase()) > -1);
          return res;
        }
      }))
      .subscribe(res => {
          this.displayedClientes = res;

      });
  }
}
