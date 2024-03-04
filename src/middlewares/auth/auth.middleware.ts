import {
  ForbiddenException,
  Injectable,
  NestMiddleware,
  UnauthorizedException,
} from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';

@Injectable()
export class AuthMiddleware implements NestMiddleware {
  constructor(private jwtService: JwtService) {}
  use(req: Request, res: Response, next: () => void) {
    // console.log(req.headers);
    const authHeader = req.headers['authorization'];
    if (!authHeader?.startsWith('Bearer ')) {
      throw new UnauthorizedException();
    }
    const token = authHeader?.split(' ')[1];
    try {
      const payload = this.jwtService.verify(token);
      req['account'] = payload;
      next();
    } catch (error) {
      throw new ForbiddenException();
    }
  }
}
