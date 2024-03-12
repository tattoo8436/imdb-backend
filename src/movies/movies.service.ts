import { BadRequestException, Injectable } from '@nestjs/common';
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
import * as dayjs from 'dayjs';
import { Rating } from 'src/entities';

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
    @InjectRepository(Rating)
    private ratingRepository: Repository<Rating>,
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
          and (:genreId::integer is null or genre.id = :genreId) 
          and (:score::integer is null or movie.score > :score) 
          and (:releaseDate::text is null or movie.releaseDate >= :releaseDate) 
          and (:language::text is null or movie.language = :language) 
          and (:numberVote::integer is null or movie.numberVote >= :numberVote)`,
        {
          name: search.name ? `%${search.name}%` : null,
          type: search.type ?? null,
          genreId: search.genreId ?? null,
          score: search.score ?? null,
          releaseDate: search.releaseDate ?? null,
          language: search.language ?? null,
          numberVote: search.numberVote ?? null,
        },
      )
      .skip((search.page - 1) * search.limit)
      .take(search.limit)
      .orderBy(`movie.${search.sortBy}`, search.orderBy);
    const [dataRaw, totals] = await queryBuilder.getManyAndCount();

    const data = [];
    for (let i = 0; i < dataRaw.length; i++) {
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

  async getMovieById(id: number) {
    const queryBuilder = this.movieRepository.createQueryBuilder('movie');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('movie.movieGenres', 'movieGenre')
      .leftJoinAndSelect('movieGenre.genre', 'genre')
      .leftJoinAndSelect('movie.movieActors', 'movieActor')
      .leftJoinAndSelect('movieActor.actor', 'actor')
      .leftJoinAndSelect('movie.movieDirectors', 'movieDirector')
      .leftJoinAndSelect('movieDirector.director', 'director')
      .leftJoinAndSelect('movie.episodes', 'episodes')
      .leftJoinAndSelect('movie.comments', 'comments')
      .leftJoinAndSelect('comments.account', 'account')
      .where(`movie.id = :id`, {
        id: id,
      })
      .orderBy('episodes.ep', 'ASC')
      .orderBy('comments.id', 'DESC');
    const data = await queryBuilder.getOne();
    return data;
  }

  async createMovie(movieRequest: BaseMovie) {
    const movie: Movie = {
      ...movieRequest,
      id: null,
      ended: false,
      numberSeason: movieRequest.type === 1 ? null : 1,
      numberVote: 0,
      score: 0,
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

  async addSeason(id: number) {
    const movie = await this.movieRepository.findOneBy({ id });
    if (movie) {
      movie.numberSeason++;
      return this.movieRepository.save(movie);
    } else {
      throw new BadRequestException();
    }
  }

  async getTrendingMovie() {
    const date = dayjs().subtract(1, 'year').format('YYYY-MM-DD');
    const queryBuilder = this.ratingRepository.createQueryBuilder('rating');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('rating.movie', 'movie')
      .where(`rating.date > :date`, {
        date,
      });
    const ratings = await queryBuilder.getMany();
    const arr = [];
    ratings.map((i) => {
      if (i.movie) {
        arr.push(i.movie.id);
      }
    });
    return arr;
  }

  async getMoviesSimilar(movieId: number) {
    const queryBuilder = this.movieRepository.createQueryBuilder('movie');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('movie.movieGenres', 'movieGenres')
      .leftJoinAndSelect('movieGenres.genre', 'genre')
      .where(`movie.id = :id`, {
        id: movieId,
      });
    const movie = await queryBuilder.getOne();
    const arr = [];
    const queryBuilder2 = this.movieRepository.createQueryBuilder('movie');
    queryBuilder2
      .distinct()
      .leftJoinAndSelect('movie.movieGenres', 'movieGenres')
      .leftJoinAndSelect('movieGenres.genre', 'genre');
    const movies = await queryBuilder2.getMany();
    // console.log(movies);

    movies.map((i) => {
      let count = 0;
      movie.movieGenres.map((mg) => {
        for (let k = 0; k < Math.min(2, i.movieGenres.length); k++) {
          if (mg.genre.id === i.movieGenres[k].genre.id) {
            count++;
          }
        }
      });
      if (count >= 2 && movie.id !== i.id) {
        arr.push(i);
      }
    });
    return arr;
  }
}
