import {
  Column,
  Entity,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { MovieGenre } from './MovieGenre';
import { Rating } from './Rating';
import { Comment } from './Comment';
import { Movie } from './Movie';

@Entity({ name: 'episodes' })
export class Episode {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  ep: number;

  @Column()
  season: number;

  @Column()
  name: string;

  @Column({ nullable: true, length: 1000 })
  description: string;

  @Column({ nullable: true })
  image: string;

  @Column({ nullable: true })
  releaseDate: string;

  @Column({ nullable: true })
  duration: number;

  @Column({ nullable: true })
  numberVote: number;

  @Column({ nullable: true })
  score: number;

  @ManyToOne(() => Movie, (movie) => movie.episodes, { onDelete: 'SET NULL' })
  movie: Movie;

  @OneToMany(() => Rating, (rating) => rating.episode)
  ratings: Rating[];

  @OneToMany(() => Comment, (comment) => comment.episode)
  comments: Comment[];
}
