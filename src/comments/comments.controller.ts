import { Body, Controller, Post, Req } from '@nestjs/common';
import { CommentsService } from './comments.service';
import { BaseComment } from './dtos/BaseComment';

@Controller('api/comments')
export class CommentsController {
  constructor(private commentService: CommentsService) {}

  @Post('')
  createComment(@Body() comment: BaseComment, @Req() req: any) {
    return this.commentService.createComment(comment, req.account?.id);
  }
}
