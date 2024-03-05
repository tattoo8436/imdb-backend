import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Put,
} from '@nestjs/common';
import { EpisodeCreate } from './dtos/EpisodeCreate';
import { EpisodeUpdate } from './dtos/EpisodeUpdate';
import { EpisodesService } from './episodes.service';

@Controller('api/episodes')
export class EpisodesController {
  constructor(private episodeService: EpisodesService) {}

  @Get('/:id')
  getEpisodeById(@Param('id') id: number) {
    return this.episodeService.getEpisodeById(id);
  }

  @Post('')
  createEpisode(@Body() episode: EpisodeCreate) {
    return this.episodeService.createEpisode(episode);
  }

  @Put('')
  updateEpisode(@Body() episode: EpisodeUpdate) {
    return this.episodeService.updateEpisode(episode);
  }

  @Delete('/:id')
  deleteEpisode(@Param('id') id: number) {
    return this.episodeService.deleteEpisode(id);
  }
}
