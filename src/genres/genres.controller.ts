import {
  Controller,
  Post,
  Body,
  Put,
  Delete,
  Param,
  ParseIntPipe,
  Request,
} from '@nestjs/common';
import { GenresService } from './genres.service';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { GenreSearch } from './dtos/GenreSearch.dto';
import { BaseGenre } from './dtos/BaseGenre.dto';
import { GenreUpdate } from './dtos/GenreUpdate.dto';

@ApiTags('Genres')
@ApiBearerAuth()
@Controller('api/genres')
export class GenresController {
  constructor(private genreService: GenresService) {}

  @ApiOperation({ summary: 'Search genre' })
  @Post('/search')
  searchGenres(@Body() search: GenreSearch) {
    return this.genreService.searchGenres(search);
  }

  @ApiOperation({ summary: 'Create genre' })
  @Post('')
  createGenre(@Request() req: any, @Body() genre: BaseGenre) {
    console.log(req.account);
    return this.genreService.createGenre(genre);
  }

  @ApiOperation({ summary: 'Update genre' })
  @Put('')
  updateGenre(@Body() genre: GenreUpdate) {
    return this.genreService.updateGenre(genre);
  }

  @ApiOperation({ summary: 'Delete genre' })
  @Delete('/:id')
  deleteGenre(@Param('id', ParseIntPipe) id: number) {
    return this.genreService.deleteGenre(id);
  }
}
