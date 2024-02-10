import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';


import {of} from 'rxjs';
import {ClienteService} from '../../services/cliente.service';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {ClienteCrudComponent} from './cliente-crud.component';
import {ActivatedRoute, convertToParamMap} from '@angular/router';

describe('ClienteCrudComponent', () => {
  let component: ClienteCrudComponent;
  let fixture: ComponentFixture<ClienteCrudComponent>;
  const clienteServiceMock = jasmine.createSpyObj('clienteService', ['getClienteById']);

  beforeEach(waitForAsync(() => {
    clienteServiceMock.getClienteById.and.returnValue(of(
        {
          personaId: 1,
          identificacion: 'Identificacion 1',
          nombre: 'Nombre 1',
          direccion: 'C/nombre calle #25, sector 1',
          telefono: '###-###-####',
          contrasenia: '123',
          genero: 'M',
          edad: 30,
          estado: true
        }
      ));
    TestBed.configureTestingModule({
      declarations: [ClienteCrudComponent],
      providers: [
        {provide: ClienteService, useValue: clienteServiceMock},
        {provide: ActivatedRoute, useValue: {snapshot: {paramMap: convertToParamMap({id: '123'})}}}
      ],
      imports: [RouterTestingModule, ReactiveFormsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get cliente by ID', () => {
    expect(clienteServiceMock.getClienteById).toHaveBeenCalled();
  });

});
