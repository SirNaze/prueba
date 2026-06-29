import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from './cliente.model';

@Injectable()
export class ClienteService {
  private url = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Cliente[]>(this.url);
  }

  create(c: Cliente) {
    return this.http.post(this.url, c);
  }

  update(id: number, c: Cliente) {
    return this.http.put(`${this.url}/${id}`, c);
  }

  delete(id: number) {
    return this.http.delete(`${this.url}/${id}`);
  }
}