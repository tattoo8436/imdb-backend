import { Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Director } from './Director';
import { Movie } from './Movie';

@Entity({ name: 'movie_director' })
export class MovieDirector {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne(() => Movie, (movie) => movie.movieDirectors, {
    onDelete: 'SET NULL',
  })
  movie: Movie;

  @ManyToOne(() => Director, (director) => director.movieDirectors)
  director: Director;
}
