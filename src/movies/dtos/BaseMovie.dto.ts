import { IsNotEmpty } from '@nestjs/class-validator';

export class BaseMovie {
  @IsNotEmpty()
  name: string;

  type: number;

  description: string;

  trailer: string;

  image: string;

  releaseDate: string;

  duration: number;

  language: string;

  genreIds: number[];

  actors: any[];

  directorIds: number[];
}
