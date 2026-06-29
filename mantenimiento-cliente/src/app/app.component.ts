import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from './cliente.service';
import { Cliente } from './cliente.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  form: FormGroup;
  clientes: Cliente[] = [];
  selectedId: number = null;
  mensaje = '';

  constructor(private fb: FormBuilder, private svc: ClienteService) {
    const soloLetras = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    const fechaPattern = /^\d{2}\/\d{2}\/\d{4}$/;

    this.form = this.fb.group({
      nombres:           ['', [Validators.required, Validators.pattern(soloLetras)]],
      apellidoPaterno:   ['', [Validators.required, Validators.pattern(soloLetras)]],
      apellidoMaterno:   ['', [Validators.required, Validators.pattern(soloLetras)]],
      fechaNacimiento:   ['', [Validators.required, Validators.pattern(fechaPattern)]],
      sexo:              ['', Validators.required],
      direccion:         ['', Validators.required],
      correoElectronico: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit() { this.cargar(); }

  cargar() {
    this.svc.getAll().subscribe(data => this.clientes = data);
  }
  onFecha(event: any) {
  const partes = event.target.value.split('-'); // viene YYYY-MM-DD
  const fecha = `${partes[2]}/${partes[1]}/${partes[0]}`; // lo convierte a DD/MM/YYYY
  this.form.get('fechaNacimiento').setValue(fecha);
  }

  seleccionar(c: Cliente) {
  if (this.selectedId === c.idCliente) {
    this.limpiar();
  } else {
    this.selectedId = c.idCliente;
    this.form.patchValue(c);
    this.mensaje = '';
  }
}

  registrar() {
  Object.keys(this.form.controls).forEach(key => this.form.get(key).markAsTouched());
  if (this.form.invalid) return;
  this.svc.create(this.form.value).subscribe((res: any) => {
    this.mensaje = res.mensaje;
    this.limpiar(); this.cargar();
  });
}

  actualizar() {
  Object.keys(this.form.controls).forEach(key => this.form.get(key).markAsTouched());
  if (!this.selectedId || this.form.invalid) return;
  this.svc.update(this.selectedId, this.form.value).subscribe((res: any) => {
    this.mensaje = res.mensaje;
    this.limpiar(); this.cargar();
  });
}

  eliminar() {
    if (!this.selectedId) return;
    if (!confirm('¿Desea eliminar el cliente seleccionado?')) return;
    this.svc.delete(this.selectedId).subscribe((res: any) => {
      this.mensaje = res.mensaje;
      this.limpiar(); this.cargar();
    });
  }

  limpiar() {
    this.form.reset();
    this.selectedId = null;
    setTimeout(() => this.mensaje = '', 3000);
  }

  err(campo: string) {
    const c = this.form.get(campo);
    return c.invalid && c.touched;
  }
  
}