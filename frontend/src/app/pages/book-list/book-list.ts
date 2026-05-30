import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { Book } from '../../models/book';
import { BookService } from '../../services/book';
import { RouterLink } from '@angular/router';
import { MatRippleModule } from '@angular/material/core';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    MatRippleModule,],
  templateUrl: './book-list.html',
  styleUrl: './book-list.css',
})
export class BookList implements OnInit {
  private readonly bookService = inject(BookService);

  protected readonly books = signal<Book[]>([]);
  protected readonly loading = signal<boolean>(true);
  protected readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.fetchBooks();
  }

  protected fetchBooks(): void {
    this.loading.set(true);
    this.error.set(null);
    this.bookService.list().subscribe({
      next: (books) => {
        this.books.set(books);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Failed to load books', err);
        this.error.set('Could not load books. Is the Spring backend running on :8080?');
        this.loading.set(false);
      },
    });
  }

  protected authorNames(book: Book): string {
    if (!book.authors?.length) {
      return 'Unknown author';
    }
    return book.authors.map((a) => `${a.firstName} ${a.lastName}`).join(', ');
  }
}
