import { ApiProperty } from '@nestjs/swagger';
import { BaseAccount } from './BaseAccount.dto';
import { IsEmail, IsNotEmpty } from '@nestjs/class-validator';

export class AccountRegister extends BaseAccount {
  @ApiProperty({ example: 'account1@gmail.com' })
  @IsNotEmpty()
  @IsEmail()
  email: string;
}
