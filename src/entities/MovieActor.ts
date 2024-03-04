import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Actor } from './Actor';
import { Movie } from './Movie';

@Entity({ name: 'movie_actor' })
export class MovieActor {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  nameInMovie: string;

  @ManyToOne(() => Movie, (movie) => movie.movieActors, {
    onDelete: 'SET NULL',
  })
  movie: Movie;

  @ManyToOne(() => Actor, (actor) => actor.movieActors)
  actor: Actor;
}
