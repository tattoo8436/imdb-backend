import {
  Controller,
  FileTypeValidator,
  Get,
  MaxFileSizeValidator,
  Param,
  ParseFilePipe,
  Post,
  Req,
  Res,
  UploadedFile,
  UploadedFiles,
  UseInterceptors,
} from '@nestjs/common';
import {
  AnyFilesInterceptor,
  FileFieldsInterceptor,
  FileInterceptor,
} from '@nestjs/platform-express';
import {
  ApiBearerAuth,
  ApiBody,
  ApiConsumes,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';
import { FilesService } from './files.service';
import { Response } from 'express';

@ApiTags('File')
@ApiBearerAuth()
@Controller('api/files')
export class FilesController {
  constructor(private fileService: FilesService) {}

  @ApiOperation({ summary: 'Upload image' })
  @ApiConsumes('multipart/form-data')
  @ApiBody({
    schema: {
      type: 'object',
      properties: {
        image: {
          type: 'string',
          format: 'binary',
        },
      },
    },
  })
  @Post('/image')
  @UseInterceptors(FileInterceptor('image'))
  uploadImage(
    @UploadedFile(
      new ParseFilePipe({
        validators: [
          new MaxFileSizeValidator({ maxSize: 1024 * 1024 * 10 }),
          new FileTypeValidator({ fileType: 'image' }),
        ],
      }),
    )
    image: any,
  ) {
    console.log(image);
    return this.fileService.saveImage(image);
  }

  @ApiOperation({ summary: 'Get image' })
  @Get('/image/:id')
  getImage(@Param('id') id: string, @Res() res: Response) {
    return this.fileService.getImage(id, res);
  }
}
