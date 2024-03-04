import { ApiProperty } from '@nestjs/swagger';

export class BaseFile {
  @ApiProperty({ type: 'string', format: 'binary' })
  file: Express.Multer.File;
}
