import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Book } from '../models/book';

@Injectable({ providedIn: 'root' })
export class BookService {
  private readonly http = inject(HttpClient);
  // TODO: move to an environment file once we wire up production builds.
  private readonly baseUrl = 'http://localhost:8080/api/books';

  list(): Observable<Book[]> {
    return this.http.get<Book[]>(this.baseUrl);
  }

  get(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${id}`);
  }
}
