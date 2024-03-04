import { Injectable } from '@nestjs/common';
import { Response } from 'express';
import * as fs from 'fs';
import * as path from 'path';
import { v4 as uuidv4 } from 'uuid';

@Injectable()
export class FilesService {
  saveImage(image: Express.Multer.File) {
    const newName = uuidv4() + image.originalname;
    // console.log(process.cwd());
    const filePath = path.join(
      process.cwd(),
      'src',
      'uploads',
      'images',
      newName,
    );
    fs.writeFileSync(filePath, image.buffer);
    console.log(__dirname);
    console.log(process.cwd());

    return newName;
  }

  getImage(id: string, res: Response) {
    const filePath = path.join(process.cwd(), 'src', 'uploads', 'images', id);
    console.log(filePath);
    return res.sendFile(filePath);
  }
}
