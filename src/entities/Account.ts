import { PrimaryGeneratedColumn, Column, Entity, OneToMany } from 'typeorm';
import { Rating } from './Rating';
import { Comment } from './Comment';

@Entity({ name: 'accounts' })
export class Account {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  username: string;

  @Column()
  password: string;

  @Column()
  email: string;

  @Column()
  role: number;

  @OneToMany(() => Rating, (rating) => rating.account)
  ratings: Rating[];

  @OneToMany(() => Comment, (comment) => comment.account)
  comments: Comment[];
}
