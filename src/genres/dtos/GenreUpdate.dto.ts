import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { BaseGenre } from './BaseGenre.dto';

export class GenreUpdate extends BaseGenre {
  @ApiProperty({ format: 'binary', type: 'string' })
  @IsNotEmpty()
  id: number;
}
