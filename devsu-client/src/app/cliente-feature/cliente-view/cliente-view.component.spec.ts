import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import {ClienteViewComponent} from './cliente-view.component';
import {ClienteService} from '../../services/cliente.service';
import {of} from 'rxjs';
import {FormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';

describe('ClienteViewComponent', () => {
  let component: ClienteViewComponent;
  let fixture: ComponentFixture<ClienteViewComponent>;
  const clienteServiceMock = jasmine.createSpyObj('clienteService', ['getAllClientes']);


  beforeEach(
    waitForAsync(() => {
      clienteServiceMock.getAllClientes.and.returnValue(of([
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
          },
          {
            personaId: 2,
            identificacion: 'Identificacion 2',
            nombre: 'Nombre 2',
            direccion: 'C/nombre calle #25, sector 2',
            telefono: '###-###-####',
            contrasenia: '123',
            genero: 'F',
            edad: 20,
            estado: true
          },
          {
            personaId: 3,
            identificacion: 'Identificacion 3',
            nombre: 'Nombre 3',
            direccion: 'C/nombre calle #25, sector 3',
            telefono: '###-###-####',
            contrasenia: '123',
            genero: 'M',
            edad: 26,
            estado: true
          }
        ]));

      TestBed.configureTestingModule({
        declarations: [ClienteViewComponent],
        providers: [
          {provide: ClienteService, useValue: clienteServiceMock}
        ],
        imports: [FormsModule, RouterTestingModule],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get all clientes', () => {
    expect(clienteServiceMock.getAllClientes).toHaveBeenCalled();
  });

});
