import { Body, Controller, Delete, Param, Post, Put } from '@nestjs/common';
import { DirectorsService } from './directors.service';
import { DirectorSearch } from './dtos/DirectorSearch';
import { BaseDirector } from './dtos/BaseDirector';
import { DirectorUpdate } from './dtos/DirectorUpdate';

@Controller('api/directors')
export class DirectorsController {
  constructor(private directorService: DirectorsService) {}

  @Post('/search')
  searchDirector(@Body() search: DirectorSearch) {
    return this.directorService.searchDirectors(search);
  }

  @Post('')
  createDirector(@Body() director: BaseDirector) {
    return this.directorService.createDirector(director);
  }

  @Put('')
  updateDirector(@Body() director: DirectorUpdate) {
    return this.directorService.updateDirector(director);
  }

  @Delete('/:id')
  deleteDirector(@Param('id') id: number) {
    return this.directorService.deleteDirector(id);
  }
}
