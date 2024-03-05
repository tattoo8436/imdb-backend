import { IsNotEmpty } from '@nestjs/class-validator';

export class BaseRating {
  @IsNotEmpty()
  score: number;

  movieId?: number;

  episodeId?: number;
}
