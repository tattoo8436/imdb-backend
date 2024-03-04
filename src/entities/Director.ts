import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from 'typeorm';
import { MovieDirector } from './MovieDirector';

@Entity({ name: 'directors' })
export class Director {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column({ nullable: true })
  image: string;

  @Column({ nullable: true, length: 1000 })
  description: string;

  @Column({ nullable: true })
  dob: string;

  @OneToMany(() => MovieDirector, (movieDirector) => movieDirector.director)
  movieDirectors: MovieDirector[];
}
