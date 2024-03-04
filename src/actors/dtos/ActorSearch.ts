import { ApiProperty } from '@nestjs/swagger';

export class ActorSearch {
  @ApiProperty({ example: 'a' })
  name: string;

  @ApiProperty({ example: 1 })
  page: number;

  @ApiProperty({ example: 10 })
  limit: number;
}
