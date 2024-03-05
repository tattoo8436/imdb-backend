import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from 'typeorm';
import { MovieGenre } from './MovieGenre';
import { Rating } from './Rating';
import { Comment } from './Comment';
import { MovieActor } from './MovieActor';
import { MovieDirector } from './MovieDirector';
import { Episode } from './Episode';

@Entity({ name: 'movies' })
export class Movie {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column()
  type: number;

  @Column({ nullable: true, length: 1000 })
  description: string;

  @Column({ nullable: true })
  trailer: string;

  @Column({ nullable: true })
  image: string;

  @Column({ nullable: true })
  releaseDate: string;

  @Column({ nullable: true })
  duration: number;

  @Column({ nullable: true })
  ended: boolean;

  @Column({ nullable: true })
  numberSeason: number;

  @Column({ nullable: true })
  numberVote: number;

  @Column({ nullable: true, type: 'float' })
  score: number;

  @Column({ nullable: true })
  language: string;

  @OneToMany(() => Episode, (episode) => episode.movie)
  episodes: Episode[];

  @OneToMany(() => MovieActor, (movieActor) => movieActor.movie)
  movieActors: MovieActor[];

  @OneToMany(() => MovieDirector, (movieDirector) => movieDirector.movie)
  movieDirectors: MovieDirector[];

  @OneToMany(() => MovieGenre, (movieGenre) => movieGenre.movie)
  movieGenres: MovieGenre[];

  @OneToMany(() => Rating, (rating) => rating.movie)
  ratings: Rating[];

  @OneToMany(() => Comment, (comment) => comment.movie)
  comments: Comment[];
}
