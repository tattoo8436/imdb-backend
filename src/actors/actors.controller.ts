import { Body, Controller, Delete, Param, Post, Put } from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { ActorsService } from './actors.service';
import { BaseActor } from './dtos/BaseActor';
import { ActorUpdate } from './dtos/ActorUpdate';
import { ActorSearch } from './dtos/ActorSearch';

@ApiBearerAuth()
@ApiTags('Actors')
@Controller('api/actors')
export class ActorsController {
  constructor(private actorService: ActorsService) {}

  @ApiOperation({ summary: 'Search actor' })
  @Post('/search')
  searchActor(@Body() search: ActorSearch) {
    return this.actorService.searchActors(search);
  }

  @ApiOperation({ summary: 'Create actor' })
  @Post('')
  createActor(@Body() actor: BaseActor) {
    return this.actorService.createActor(actor);
  }

  @ApiOperation({ summary: 'Update actor' })
  @Put('')
  updateActor(@Body() actor: ActorUpdate) {
    return this.actorService.updateActor(actor);
  }

  @ApiOperation({ summary: 'Delete actor' })
  @Delete('/:id')
  deleteActor(@Param('id') id: number) {
    return this.actorService.deleteActor(id);
  }
}
