
import { AcGameObject } from './AcGameObject';

export class Circle extends AcGameObject{


        constructor(x,y,r,color,ctx){
            super();
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = color;
            this.dx = Math.random() * 12 - 7;
            this.dy = Math.random() * 12 - 7;
            this.ctx = ctx;
        }

        start(){
            this.render();
           
        }
        update(){
             super.destory();
          
        }
        render(){
            this.x += this.dx;
            this.y += this.dy;
            this.r--;
            //新建一条路径
            this.ctx.beginPath();
            //创建一个圆
            this.ctx.arc(this.x, this.y, this.r, 0, Math.PI*2, true);
            //设置样式颜色
            this.ctx.fillStyle = this.color;
            //通过填充路径的内容区域生成实心的图形
            this.ctx.fill();
        }

}