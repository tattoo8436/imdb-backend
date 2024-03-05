import { IsNotEmpty } from '@nestjs/class-validator';

export class BaseEpisode {
  @IsNotEmpty()
  season: number;

  @IsNotEmpty()
  name: string;

  description: string;

  image: string;

  releaseDate: string;

  duration: number;
}
