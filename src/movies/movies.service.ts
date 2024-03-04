import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { MovieSearch } from './dtos/MovieSearch.dto';
import { initSearch } from 'src/utils';
import { BaseMovie } from './dtos/BaseMovie.dto';
import { MovieUpdate } from './dtos/MovieUpdate.dto';
import { Movie } from 'src/entities/Movie';
import { MovieGenre } from 'src/entities/MovieGenre';
import { Genre } from 'src/entities/Genre';
import { Actor } from 'src/entities/Actor';
import { MovieActor } from 'src/entities/MovieActor';
import { Director } from 'src/entities/Director';
import { MovieDirector } from 'src/entities/MovieDirector';

@Injectable()
export class MoviesService {
  constructor(
    @InjectRepository(Movie) private movieRepository: Repository<Movie>,
    @InjectRepository(MovieGenre)
    private movieGenreRepository: Repository<MovieGenre>,
    @InjectRepository(Genre) private genreRepository: Repository<Genre>,
    @InjectRepository(Actor) private actorRepository: Repository<Actor>,
    @InjectRepository(MovieActor)
    private movieActorRepository: Repository<MovieActor>,
    @InjectRepository(Director)
    private directorRepository: Repository<Director>,
    @InjectRepository(MovieDirector)
    private movieDirectorRepository: Repository<MovieDirector>,
  ) {}

  async searchMovies(searchRaw: MovieSearch) {
    const search = initSearch(searchRaw);
    const queryBuilder = this.movieRepository.createQueryBuilder('movie');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('movie.movieGenres', 'movieGenre')
      .leftJoinAndSelect('movieGenre.genre', 'genre')
      .leftJoinAndSelect('movie.movieActors', 'movieActor')
      .leftJoinAndSelect('movieActor.actor', 'actor')
      .leftJoinAndSelect('movie.movieDirectors', 'movieDirector')
      .leftJoinAndSelect('movieDirector.director', 'director')
      .where(
        `(:name::text is null or lower(movie.name) ILIKE :name)
         and (:type::integer is null or movie.type = :type)
          and (:genreId::integer is null or genre.id = :genreId)`,
        {
          name: `%${search.name}%`,
          type: search.type,
          genreId: search.genreId,
        },
      )
      .skip((search.page - 1) * search.limit)
      .take(search.limit)
      .orderBy(`movie.${search.sortBy}`, search.orderBy);
    const [dataRaw, totals] = await queryBuilder.getManyAndCount();

    const data = [];
    for (let i = 0; i < totals; i++) {
      const queryBuilder2 = this.movieRepository.createQueryBuilder('movie');
      queryBuilder2
        .distinct()
        .leftJoinAndSelect('movie.movieGenres', 'movieGenre')
        .leftJoinAndSelect('movieGenre.genre', 'genre')
        .leftJoinAndSelect('movie.movieActors', 'movieActor')
        .leftJoinAndSelect('movieActor.actor', 'actor')
        .leftJoinAndSelect('movie.movieDirectors', 'movieDirector')
        .leftJoinAndSelect('movieDirector.director', 'director')
        .where(`movie.id = :id`, {
          id: dataRaw[i].id,
        });
      const movie = await queryBuilder2.getOne();
      data.push(movie);
    }
    return { data, totals };
  }

  async createMovie(movieRequest: BaseMovie) {
    const movie: Movie = {
      ...movieRequest,
      id: null,
      ended: false,
      numberSeason: movieRequest.type === 1 ? null : 1,
      numberVote: 0,
      score: null,
      episodes: null,
      movieActors: null,
      movieDirectors: null,
      movieGenres: null,
      ratings: null,
      comments: null,
    };
    const movieSaved = await this.movieRepository.save(movie);

    for (let i = 0; i < movieRequest.genreIds.length; i++) {
      const genre = await this.genreRepository.findOneBy({
        id: movieRequest.genreIds[i],
      });
      await this.movieGenreRepository.save({
        movie: movieSaved,
        genre,
      });
    }

    for (let i = 0; i < movieRequest.actors.length; i++) {
      const actor = await this.actorRepository.findOneBy({
        id: movieRequest.actors[i]?.id,
      });
      await this.movieActorRepository.save({
        movie: movieSaved,
        actor,
        nameInMovie: movieRequest.actors[i]?.nameInMovie,
      });
    }

    for (let i = 0; i < movieRequest.directorIds.length; i++) {
      const director = await this.directorRepository.findOneBy({
        id: movieRequest.directorIds[i],
      });
      await this.movieDirectorRepository.save({
        movie: movieSaved,
        director,
      });
    }

    return movieSaved;
  }

  async updateMovie(movieRequest: MovieUpdate) {
    const movieFound = await this.movieRepository.findOneBy({
      id: movieRequest.id,
    });
    const { genreIds, actors, directorIds, ...rest } = movieRequest;
    console.log(genreIds, actors, directorIds);

    const movie = {
      ...movieFound,
      ...rest,
    };
    const movieSaved = await this.movieRepository.save(movie);

    await this.movieGenreRepository.delete({ movie: movieFound });
    await this.movieActorRepository.delete({ movie: movieFound });
    await this.movieDirectorRepository.delete({ movie: movieFound });

    for (let i = 0; i < movieRequest.genreIds.length; i++) {
      const genre = await this.genreRepository.findOneBy({
        id: movieRequest.genreIds[i],
      });
      await this.movieGenreRepository.save({
        movie: movieSaved,
        genre,
      });
    }

    for (let i = 0; i < movieRequest.actors.length; i++) {
      const actor = await this.actorRepository.findOneBy({
        id: movieRequest.actors[i]?.id,
      });
      await this.movieActorRepository.save({
        movie: movieSaved,
        actor,
        nameInMovie: movieRequest.actors[i]?.nameInMovie,
      });
    }

    for (let i = 0; i < movieRequest.directorIds.length; i++) {
      const director = await this.directorRepository.findOneBy({
        id: movieRequest.directorIds[i],
      });
      await this.movieDirectorRepository.save({
        movie: movieSaved,
        director,
      });
    }

    return movieSaved;
  }

  deleteMovie(id: number) {
    try {
      return this.movieRepository.delete({ id });
    } catch (error) {
      throw new Error();
    }
  }
}
