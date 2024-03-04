import { IsNotEmpty } from '@nestjs/class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { BaseActor } from './BaseActor';

export class ActorUpdate extends BaseActor {
  @ApiProperty({ example: 1 })
  @IsNotEmpty()
  id: number;
}
