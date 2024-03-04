import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class BaseAccount {
  @ApiProperty({ example: 'account1' })
  @IsNotEmpty()
  username: string;

  @IsNotEmpty()
  @ApiProperty({ example: '123' })
  password: string;
}
