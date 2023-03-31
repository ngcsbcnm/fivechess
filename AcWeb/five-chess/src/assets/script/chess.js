import { AcGameObject } from "./AcGameObject";

export class Chess extends AcGameObject {
        constructor(color, board) {
                super();
                this.color = color;
                this.board = board;
                this.g = [];
                this.status = "idle";
                this.mythiswin = [];
                this.pcthiswin = [];
        }

        setColor(color) {
                this.color = color;
        }
        setPos(x, y) {
                this.g.push([x, y]);
        }
        AI(mythiswin) {
                let myScore = [];
                let pcScore = [];
                let max = 0;
                let u = 0;
                let v = 0;
                for (let i = 0; i < this.board.size - 1; i++) {
                        myScore[i] = [];
                        pcScore[i] = [];
                        for (let j = 0; j < this.board.size - 1; j++) {
                                // 初始为0
                                myScore[i][j] = 0;
                                pcScore[i][j] = 0;
                        }
                }

                for (let i = 0; i < this.board.size - 1; i++) {
                        for (let j = 0; j < this.board.size - 1; j++) {
                                if (this.board.g[i][j] == 0) {
                                        for (let k = 0; k <= this.board.count; k++) {
                                                if (this.board.allwins[i][j][k]) {
                                                        if (mythiswin[k] == 1) {
                                                                myScore[i][j] += 200;
                                                        } else if (mythiswin[k] == 2) {
                                                                myScore[i][j] += 400;
                                                        } else if (mythiswin[k] == 3) {
                                                                myScore[i][j] += 1000;
                                                        } else if (mythiswin[k] == 4) {
                                                                myScore[i][j] += 10000;
                                                        }

                                                        if (this.pcthiswin[k] == 1) {
                                                                pcScore[i][j] += 220;
                                                        } else if (this.pcthiswin[k] == 2) {
                                                                pcScore[i][j] += 420;
                                                        } else if (this.pcthiswin[k] == 3) {
                                                                pcScore[i][j] += 1100;
                                                        } else if (this.pcthiswin[k] == 4) {
                                                                pcScore[i][j] += 20000;
                                                        }
                                                }
                                        }

                                        if (myScore[i][j] > max) {
                                                max = myScore[i][j];
                                                u = i;
                                                v = j;
                                        } else if (myScore[i][j] == max) {
                                                if (pcScore[i][j] > pcScore[u][v]) {
                                                        u = i;
                                                        v = j;
                                                }
                                        }

                                        if (pcScore[i][j] > max) {
                                                max = pcScore[i][j];
                                                u = i;
                                                v = j;
                                        } else if (pcScore[i][j] == max) {
                                                if (myScore[i][j] > myScore[u][v]) {
                                                        u = i;
                                                        v = j;
                                                }
                                        }
                                }
                        }
                }

                //this.board.g[u][v] = true;
                //console.log("机器落子坐标  " + u + "," + v);
                // for (let k = 0; k <= this.board.count; k++) {
                //         if (this.board.allwins[u][v][k]) {
                //                 this.pcthiswin[k]++;
                //                 mythiswin[k] = 60;
                //                 // if (this.pcthiswin[k] == 5) {
                //                 // alert("很遗憾，电脑赢！");
                //                 // over = true;
                //                 }
                // }

                return [u, v];
        }
        update() {
                this.render();
        }

        start() { }

        render() {
                const ctx = this.board.ctx;
                const L = this.board.L;
                ctx.fillStyle = this.color;

                for (let i in this.g) {
                        const t = this.g[i];
                        const x = t[0] * L, y = t[1] * L;

                        ctx.beginPath();
                        ctx.arc(x, y, L / 3, 0, 2 * Math.PI);
                        ctx.fill();
                        // console.log(ctx);
                }

        }
}
