import { Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Genre } from './Genre';
import { Movie } from './Movie';

@Entity({ name: 'movie_genre' })
export class MovieGenre {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne(() => Movie, (movie) => movie.movieGenres, {
    onDelete: 'SET NULL',
  })
  movie: Movie;

  @ManyToOne(() => Genre, (genre) => genre.movieGenres)
  genre: Genre;
}
