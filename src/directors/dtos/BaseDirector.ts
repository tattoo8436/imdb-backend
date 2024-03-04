import { ApiProperty } from '@nestjs/swagger';

export class BaseDirector {
  @ApiProperty({ example: 'Jack' })
  name: string;

  @ApiProperty({ example: 'image.jpg' })
  image: string;

  @ApiProperty({ example: 'Đạo diễn' })
  description: string;

  @ApiProperty({ example: '1998-08-15' })
  dob: string;
}
