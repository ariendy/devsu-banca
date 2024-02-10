import {Component, OnInit} from '@angular/core';
import {MovimientoService} from '../../services/movimiento.service';
import {Movimiento} from '../../models/movimiento.model';
import {MessageService} from '../../services/message.service';
import {CurrencyPipe, DatePipe, formatDate} from '@angular/common';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-reporte-view',
  templateUrl: './reporte-view.component.html',
  styleUrls: ['./reporte-view.component.scss']
})
export class ReporteViewComponent implements OnInit {

  start: string;
  end: string;
  movimientos: Movimiento[];

  constructor(
    private movimientoService: MovimientoService,
    private messageservice: MessageService,
    private fileService: FileService,
    private datePipe: DatePipe
  ) {
  }

  ngOnInit(): void {
  }

  buscarMovimientos(): void {
    if (this.validateData()) {
      this.movimientoService.getAllMovimientosByDateRange(this.start, this.end).subscribe((res: Movimiento[]) => {
          this.movimientos = res;
        },
        error => this.messageservice.sendErrorMessage(error.error));
    }

  }

  downloadJSON(): void {
    if (this.canGenerateReport()) {
      const movimientosdFormateados = this.movimientos.map(m => {
        return {
          Fecha: this.datePipe.transform(m.fecha,'dd/MM/yyyy'),
          Cliente: m.cliente,
          "Numero Cuenta": m.numeroCuenta,
          Tipo: m.tipoCuenta,
          "Saldo Inicial": m.saldoInicial,
          Estado: m.estadoCuenta,
          Movimiento: m.valor,
          "Saldo Disponible": m.saldo
        };
      })
      this.fileService.downloadJsonFile(movimientosdFormateados, 'listado-de-movimientos.json');
    }
  }

  private validateData(): boolean {

    if (!this.start) {
      this.messageservice.sendErrorMessage('Por favor introduzca la fecha de inicio.');
      return false;
    }

    if (!this.end) {
      this.messageservice.sendErrorMessage('Por favor introduzca la fecha de fin.');
      return false;
    }


    return true;
  }

  private canGenerateReport(): boolean {
    if (!(this.movimientos && this.movimientos.length)) {
      this.messageservice.sendErrorMessage('No hay movimientos para generar el reporte.');
      return false;
    }
    return true;
  }

}
