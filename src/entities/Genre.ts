import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from 'typeorm';
import { MovieGenre } from './MovieGenre';

@Entity({ name: 'genres' })
export class Genre {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @OneToMany(() => MovieGenre, (movieGenre) => movieGenre.genre)
  movieGenres: MovieGenre[];
}
