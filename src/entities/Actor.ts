import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from 'typeorm';
import { MovieActor } from './MovieActor';

@Entity({ name: 'actors' })
export class Actor {
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

  @OneToMany(() => MovieActor, (movieActor) => movieActor.actor)
  movieActors: MovieActor[];
}
