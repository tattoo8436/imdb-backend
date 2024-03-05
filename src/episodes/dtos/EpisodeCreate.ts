import { IsNotEmpty } from '@nestjs/class-validator';
import { BaseEpisode } from './BaseEpisode';

export class EpisodeCreate extends BaseEpisode {
  @IsNotEmpty()
  movieId: number;
}
