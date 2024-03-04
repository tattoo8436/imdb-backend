import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { BaseMovie } from './BaseMovie.dto';

export class MovieUpdate extends BaseMovie {
  @IsNotEmpty()
  id: number;
}
