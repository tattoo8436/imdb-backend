import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Account, Comment, Episode, Movie } from 'src/entities';
import { Repository } from 'typeorm';
import { BaseComment } from './dtos/BaseComment';
import * as dayjs from 'dayjs';

@Injectable()
export class CommentsService {
  constructor(
    @InjectRepository(Comment) private commentRepository: Repository<Comment>,
    @InjectRepository(Account) private accountRepository: Repository<Account>,
    @InjectRepository(Movie) private movieRepository: Repository<Movie>,
    @InjectRepository(Episode) private episodeRepository: Repository<Episode>,
  ) {}

  async createComment(comment: BaseComment, accountId: number) {
    let movie = null;
    let episode = null;
    if (comment.movieId) {
      movie = await this.movieRepository.findOneBy({ id: comment.movieId });
    }
    if (comment.episodeId) {
      episode = await this.episodeRepository.findOneBy({
        id: comment.episodeId,
      });
    }
    const account = await this.accountRepository.findOneBy({
      id: accountId,
    });
    const date = dayjs().format('YYYY-MM-DD');
    return this.commentRepository.save({
      account,
      content: comment.content,
      date,
      movie,
      episode,
    });
  }
}
