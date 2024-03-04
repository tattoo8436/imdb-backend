import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Movie } from './Movie';
import { Episode } from './Episode';
import { Account } from './Account';

@Entity({ name: 'ratings' })
export class Rating {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  score: number;

  @Column({ nullable: false })
  date: string;

  @ManyToOne(() => Movie, (movie) => movie.ratings, { onDelete: 'SET NULL' })
  movie: Movie;

  @ManyToOne(() => Episode, (episode) => episode.ratings)
  episode: Episode;

  @ManyToOne(() => Account, (account) => account.ratings)
  account: Account;
}
