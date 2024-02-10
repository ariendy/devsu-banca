import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Cliente} from '../../models/cliente.model';
import {ClienteService} from '../../services/cliente.service';
import {MessageService} from '../../services/message.service';

@Component({
  selector: 'app-cliente-crud',
  templateUrl: './cliente-crud.component.html',
  styleUrls: ['./cliente-crud.component.scss']
})
export class ClienteCrudComponent implements OnInit {

  form: FormGroup | undefined;
  id: number;
  selectedCliente: Cliente;

  constructor(
    private route: ActivatedRoute,
    private clienteService: ClienteService,
    private router: Router,
    private messageservice: MessageService) {
  }

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadForm();
  }

  private loadForm(): void {

    this.form = new FormGroup({
      identificacion: new FormControl(null, Validators.required),
      nombre: new FormControl(null, Validators.required),
      genero: new FormControl(null),
      edad: new FormControl(null),
      direccion: new FormControl(null),
      telefono: new FormControl(null),
      contrasenia: new FormControl(null),
      estado: new FormControl(true)
    });
    if (this.id) {
      this.clienteService.getClienteById(this.id).subscribe((res: Cliente) => {
        this.selectedCliente = res;
        this.form.patchValue({
          identificacion: res.identificacion,
          nombre: res.nombre,
          genero: res.genero,
          edad: res.edad,
          direccion: res.direccion,
          telefono: res.telefono,
          contrasenia: res.contrasenia,
          estado: res.estado
        });
      },
        error => this.messageservice.sendErrorMessage(error.error));
    }
  }

  goBackToCliente(): void {
    this.router.navigate(['/clientes']);
  }

  guardarCliente(): void {
    if (this.validateClienteForm()) {
      this.mapFormToCliente();
      if (this.selectedCliente && this.selectedCliente.personaId) {
        this.clienteService.updateCliente(this.selectedCliente).subscribe((res: Cliente) => {
          this.selectedCliente = res;
          this.messageservice.sendSuccessMessage('El cliente ha sido modificado.');
          this.goBackToCliente();
        },
          error => this.messageservice.sendErrorMessage(error.error));
      } else {
        this.clienteService.createCliente(this.selectedCliente).subscribe((res: Cliente) => {
            this.selectedCliente = res;
            this.messageservice.sendSuccessMessage('El cliente ha sido guardado.');
            this.goBackToCliente();
          },
          error => this.messageservice.sendErrorMessage(error.error));
      }
    }
  }

  private mapFormToCliente(): void {
    this.selectedCliente = {
      ...this.selectedCliente,
      identificacion: this.form.controls.identificacion.value,
      nombre: this.form.controls.nombre.value,
      genero: this.form.controls.genero.value,
      edad: this.form.controls.edad.value,
      direccion: this.form.controls.direccion.value,
      telefono: this.form.controls.telefono.value,
      contrasenia: this.form.controls.contrasenia.value,
      estado: this.form.controls.estado.value
    };

  }

  deleteCliente(): void {
    if (this.selectedCliente && this.selectedCliente.personaId) {
      this.clienteService.deleteClienteById(this.selectedCliente.personaId).subscribe(() => {
        this.messageservice.sendSuccessMessage('El cliente ha sido eliminado.');
        this.goBackToCliente();
      },
        error => this.messageservice.sendErrorMessage(error.error));
    }
  }

  private validateClienteForm(): boolean {

    if (this.form.controls.identificacion.invalid) {
      this.messageservice.sendErrorMessage('Por favor introduzca el número de identificación.');
      return false;
    }

    if (this.form.controls.nombre.invalid) {
      this.messageservice.sendErrorMessage('Por favor introduzca su nombre.');
      return false;
    }
    return true;
  }
}
