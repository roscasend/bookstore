import { Routes } from '@angular/router';

import { BookList } from './pages/book-list/book-list';
import { BookDetail } from './pages/book-detail/book-detail';

export const routes: Routes = [
  { path: '', component: BookList, title: 'Books' },
  { path: 'books/:id', component: BookDetail, title: 'Book Details' },
  { path: '**', redirectTo: '' },  
];
