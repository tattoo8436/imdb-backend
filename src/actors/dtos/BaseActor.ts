import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class BaseActor {
  @ApiProperty({ example: 'Jack' })
  name: string;

  @ApiProperty({ example: 'image.jpg' })
  image: string;

  @ApiProperty({ example: 'Diễn viên' })
  description: string;

  @ApiProperty({ example: '1998-08-15' })
  dob: string;
}
