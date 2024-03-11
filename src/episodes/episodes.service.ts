import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Episode, Movie } from 'src/entities';
import { Repository } from 'typeorm';
import { EpisodeCreate } from './dtos/EpisodeCreate';
import { EpisodeUpdate } from './dtos/EpisodeUpdate';

@Injectable()
export class EpisodesService {
  constructor(
    @InjectRepository(Episode) private episodeRepository: Repository<Episode>,
    @InjectRepository(Movie) private movieRepository: Repository<Movie>,
  ) {}

  async getEpisodeById(id: number) {
    const queryBuilder = this.episodeRepository.createQueryBuilder('episode');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('episode.comments', 'comments')
      .leftJoinAndSelect('comments.account', 'account')
      .where(`episode.id = :id`, {
        id: id,
      });
    const data = await queryBuilder.getOne();
    return data;
  }

  async createEpisode(episode: EpisodeCreate) {
    const queryBuilder = this.movieRepository.createQueryBuilder('movie');
    queryBuilder
      .distinct()
      .leftJoinAndSelect('movie.episodes', 'episodes')
      .where(`movie.id = :id`, {
        id: episode.movieId,
      });
    const movie = await queryBuilder.getOne();
    const episodes = movie.episodes.filter((i) => i.season === episode.season);
    const episodeTemp = {
      ...episode,
      ep: episodes.length + 1,
      movie,
      score: 0,
    };
    return this.episodeRepository.save(episodeTemp);
  }

  async updateEpisode(episode: EpisodeUpdate) {
    const episodeOld = await this.episodeRepository.findOneBy({
      id: episode.id,
    });
    episode = { ...episodeOld, ...episode };
    return this.episodeRepository.save(episode);
  }

  async deleteEpisode(id: number) {
    try {
      const res = await this.episodeRepository.delete({ id });
      return res;
    } catch (error) {
      throw new BadRequestException();
    }
  }
}
