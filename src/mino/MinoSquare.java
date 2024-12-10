package mino;

import enums.MinoType;

public class MinoSquare extends Mino {
  public MinoSquare() {
    super();
    create(MinoType.SQUARE);
  }

  public void setXY(int x, int y) {
    blocks[0].x = x;
    blocks[0].y = y;
    blocks[1].x = x;
    blocks[1].y = y + Block.SIZE;
    blocks[2].x = x + Block.SIZE;
    blocks[2].y = y;
    blocks[3].x = x + Block.SIZE;
    blocks[3].y = y + Block.SIZE;
  }

  public void getDirection1() {}

  public void getDirection2() {}

  public void getDirection3() {}

  public void getDirection4() {}

}
