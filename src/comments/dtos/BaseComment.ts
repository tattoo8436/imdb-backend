import { IsNotEmpty } from '@nestjs/class-validator';

export class BaseComment {
  @IsNotEmpty()
  content: string;

  movieId: number;

  episodeId: number;
}
