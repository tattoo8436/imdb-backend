import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Account, Episode, Movie, Rating } from 'src/entities';
import { Repository } from 'typeorm';
import { BaseRating } from './dtos/BaseRating';
import * as dayjs from 'dayjs';

@Injectable()
export class RatingsService {
  constructor(
    @InjectRepository(Rating) private ratingRepository: Repository<Rating>,
    @InjectRepository(Movie) private movieRepository: Repository<Movie>,
    @InjectRepository(Episode) private episodeRepository: Repository<Episode>,
    @InjectRepository(Account) private accountRepository: Repository<Account>,
  ) {}

  async getRatingMovieByAccount(movieId: number, accountId: number) {
    const queryBuilder = this.ratingRepository.createQueryBuilder('rating');
    queryBuilder
      .leftJoinAndSelect('rating.movie', 'movie')
      .leftJoinAndSelect('rating.account', 'account')
      .where(`movie.id = :movieId and account.id = :accountId`, {
        movieId,
        accountId,
      });
    const rating = await queryBuilder.getOne();
    return rating;
  }

  async getRatingEpisodeByAccount(episodeId: number, accountId: number) {
    const queryBuilder = this.ratingRepository.createQueryBuilder('rating');
    queryBuilder
      .leftJoinAndSelect('rating.episode', 'episode')
      .leftJoinAndSelect('rating.account', 'account')
      .where(`episode.id = :episodeId and account.id = :accountId`, {
        episodeId,
        accountId,
      });
    const rating = await queryBuilder.getOne();
    return rating;
  }

  async createRatingMovie(ratingRequest: BaseRating, accountId: number) {
    const rating = await this.getRatingMovieByAccount(
      ratingRequest.movieId,
      accountId,
    );
    let movieSaved = null;
    const movie = await this.movieRepository.findOneBy({
      id: ratingRequest.movieId,
    });
    let newScore = 0;
    if (rating) {
      newScore =
        (movie.score * movie.numberVote - rating.score + ratingRequest.score) /
        movie.numberVote;
      movie.score = newScore;
    } else {
      newScore =
        (movie.score * movie.numberVote + ratingRequest.score) /
        (movie.numberVote + 1);
      movie.score = newScore;
      movie.numberVote += 1;
    }
    movieSaved = await this.movieRepository.save(movie);

    const account = await this.accountRepository.findOneBy({ id: accountId });
    if (rating) {
      rating.score = ratingRequest.score;
      rating.date = dayjs().format('YYYY-MM-DD');
      await this.ratingRepository.save(rating);
      return HttpStatus.OK;
    } else {
      await this.ratingRepository.save({
        account,
        movie: movieSaved,
        episode: null,
        score: ratingRequest.score,
        date: dayjs().format('YYYY-MM-DD'),
      });
      return HttpStatus.OK;
    }
  }

  async createRatingEpisode(ratingRequest: BaseRating, accountId: number) {
    let episodeSaved = null;
    const rating = await this.getRatingEpisodeByAccount(
      ratingRequest.episodeId,
      accountId,
    );
    const episode = await this.episodeRepository.findOneBy({
      id: ratingRequest.episodeId,
    });
    const movie = await this.movieRepository.findOneBy({
      id: ratingRequest.movieId,
    });
    let newScoreEpisode = 0;
    let newScoreMovie = 0;
    if (rating) {
      newScoreEpisode =
        (episode.score * episode.numberVote -
          rating.score +
          ratingRequest.score) /
        episode.numberVote;
      episode.score = newScoreEpisode;

      newScoreMovie =
        (movie.score * movie.numberVote - rating.score + ratingRequest.score) /
        movie.numberVote;
      movie.score = newScoreMovie;
    } else {
      newScoreEpisode =
        (episode.score * episode.numberVote + ratingRequest.score) /
        (episode.numberVote + 1);
      episode.score = newScoreEpisode;
      episode.numberVote += 1;

      newScoreMovie =
        (movie.score * movie.numberVote + ratingRequest.score) /
        (movie.numberVote + 1);
      movie.score = newScoreMovie;
      movie.numberVote += 1;
    }
    episodeSaved = await this.episodeRepository.save(episode);
    await this.movieRepository.save(movie);

    const account = await this.accountRepository.findOneBy({ id: accountId });
    if (rating) {
      rating.score = ratingRequest.score;
      rating.date = dayjs().format('YYYY-MM-DD');
      await this.ratingRepository.save(rating);
      return HttpStatus.OK;
    } else {
      await this.ratingRepository.save({
        account,
        episode: episodeSaved,
        score: ratingRequest.score,
        date: dayjs().format('YYYY-MM-DD'),
      });
      return HttpStatus.OK;
    }
  }

  async getStatisticMovie(movieId: number) {
    const queryBuilder = this.ratingRepository.createQueryBuilder('rating');
    queryBuilder
      .leftJoinAndSelect('rating.movie', 'movie')
      .where(`movie.id = :movieId`, {
        movieId,
      });
    const ratings = await queryBuilder.getMany();
    const arr = Array.from({ length: 10 }, () => 0);
    for (let i = 0; i < ratings.length; i++) {
      arr[ratings[i].score - 1] += 1;
    }
    return arr;
  }
}
