import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class BaseGenre {
  @ApiProperty({ example: 'Hành động' })
  @IsNotEmpty()
  name: string;
}
