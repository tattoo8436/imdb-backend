import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { BaseDirector } from './BaseDirector';

export class DirectorUpdate extends BaseDirector {
  @ApiProperty({ example: 1 })
  @IsNotEmpty()
  id: number;
}
