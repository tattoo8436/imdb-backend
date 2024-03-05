import { IsNotEmpty } from '@nestjs/class-validator';
import { BaseEpisode } from './BaseEpisode';

export class EpisodeUpdate extends BaseEpisode {
  @IsNotEmpty()
  id: number;
}
