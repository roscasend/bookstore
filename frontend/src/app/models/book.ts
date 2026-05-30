export interface AuthorSummary {
  id: number;
  firstName: string;
  lastName: string;
}

export interface Book {
  id: number;
  title: string;
  description: string;
  imagePath: string;
  price: number;
  authors: AuthorSummary[];
}
