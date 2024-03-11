import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Movie } from './Movie';
import { Episode } from './Episode';
import { Account } from './Account';

@Entity({ name: 'comments' })
export class Comment {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  content: string;

  @Column({ nullable: false })
  date: string;

  @ManyToOne(() => Movie, (movie) => movie.comments, {
    onDelete: 'SET NULL',
  })
  movie: Movie;

  @ManyToOne(() => Episode, (episode) => episode.comments, {
    onDelete: 'SET NULL',
  })
  episode: Episode;

  @ManyToOne(() => Account, (account) => account.comments)
  account: Account;
}
