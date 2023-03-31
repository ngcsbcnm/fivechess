import { AcGameObject } from "./AcGameObject";
import { Chess } from "./chess";
//import $ from 'jquery';





export class GameBoard extends AcGameObject {
        constructor(ctx, parent, store, stack) {
                super();
                this.ctx = ctx;
                this.parent = parent;
                this.size = 16;
                this.store = store;
                this.g = [];
                this.L = 0;
                this.stack = stack;

                this.chesses = [
                        new Chess("black", this),
                        new Chess("white", this)
                ];
                this.allwins = [];
                this.count = 0;
                this.steps = 0;
                this.is_over = false;
                this.is_me = 0;
        }
        CountallWins() {
                for (let i = 0; i < 15; i++) {
                        this.allwins[i] = [];
                        for (let j = 0; j < 15; j++) {
                                this.allwins[i][j] = [];
                        }
                }

                // 横
                for (let i = 0; i < 15; i++) {
                        for (let j = 0; j < 11; j++) {
                                for (let k = 0; k < 5; k++) {
                                        this.allwins[i][j + k][this.count] = true;
                                }
                                this.count++;
                        }
                }

                // 竖
                for (let i = 0; i < 15; i++) {
                        for (let j = 0; j < 11; j++) {
                                for (let k = 0; k < 5; k++) {
                                        this.allwins[j + k][i][this.count] = true;
                                }
                                this.count++;
                        }
                }

                // 斜
                for (let i = 0; i < 11; i++) {
                        for (let j = 0; j < 11; j++) {
                                for (let k = 0; k < 5; k++) {
                                        this.allwins[i + k][j + k][this.count] = true;
                                }
                                this.count++;
                        }
                }

                // 反斜
                for (let i = 14; i > 3; i--) {
                        for (let j = 0; j < 11; j++) {
                                for (let k = 0; k < 5; k++) {
                                        this.allwins[i - k][j + k][this.count] = true;
                                }
                                this.count++;
                        }
                }
        }

        init() {
                this.CountallWins();
                for (let i = 0; i <= this.count; i++) {
                        // 在每种赢法上的初始值为0
                        this.chesses[0].mythiswin[i] = 0;
                        this.chesses[1].mythiswin[i] = 0;
                        this.chesses[0].pcthiswin[i] = 0;
                        this.chesses[1].pcthiswin[i] = 0;
                }
                for (let i = 0; i < this.size - 1; i++) {
                        this.g[i] = [];
                        for (let j = 0; j < this.size - 1; j++) {
                                this.g[i][j] = 0;
                        }
                }
        }
        start() {
                this.init();
                this.add_listener_events();
        }
        update_size() {
                this.L = parseInt(
                        Math.min(
                                this.parent.clientWidth / this.size,
                                this.parent.clientHeight / this.size
                        )
                );
                //console.log(this.parent.cilentWidth);
                this.ctx.canvas.width = this.L * this.size;
                this.ctx.canvas.height = this.L * this.size;
        }

        update() {
                this.update_size();
                this.render();
        }

        getMousePos(canvas, evt) {
                let rect = canvas.getBoundingClientRect();
                return {
                        x: evt.clientX - rect.left,
                        y: evt.clientY - rect.top,
                };
        }
        check_ready() {
                for (const c of this.chesses) {
                        if (c.status !== "idle") return false;
                }
                return true;
        }
        check_valid(i, j) {

                if (i < 1 || j < 1 || i > 15 || j > 15) return false;

                //console.log(i, j);
                if (this.g[i - 1][j - 1] !== 0) {
                        //console.log("重复");
                        return false;
                }

                return true;
        }

        check_isOver(c1, c2, i, j) {
                for (let k = 0; k <= this.count; k++) {
                        if (this.allwins[i - 1][j - 1][k]) {
                                c1.mythiswin[k]++;
                                c2.pcthiswin[k] = 50;
                                if (c1.mythiswin[k] === 5) {
                                        return true;
                                }
                        }
                }
                return false;
        }
        // draw_chess(x, y, color) {

        //         this.ctx.fillStyle = color;
        //         this.ctx.beginPath();
        //         this.ctx.arc(x, y, this.L / 3, 0, 2 * Math.PI);
        //         this.ctx.fill();
        //         console.log(x, y, color);
        // }
        add_listener_events() {
                //this.ctx.canvas.focus();
                const c1 = this.chesses[0],
                        c2 = this.chesses[1];
                // if (!this.store.state.user.is_ai_vs_human && !this.store.state.pk.is_me) {
                //         return;
                // }
                //if (this.store.state.pk.is_over !== 0) return;
                this.ctx.canvas.addEventListener("click", (e) => {


                        if (this.is_over) return;
                        if (this.store.state.user.is_ai_vs_human) {




                                let mousePos = this.getMousePos(this.ctx.canvas, e);


                                const i = Math.round(mousePos.x / this.L), j = Math.round(mousePos.y / this.L);
                                if (!this.check_valid(i, j)) return;
                                c1.g.push([i, j]);

                                //this.draw_chess(i, j, 'black');
                                this.g[i - 1][j - 1] = 2;

                                if (this.check_isOver(c1, c2, i, j)) {
                                        this.is_over = true;
                                        this.store.commit("updateAIResult", '恭喜你赢了！！！💜💙💛💚');
                                        return;
                                }


                                let [x, y] = c2.AI(c1.mythiswin);
                                this.g[x][y] = 1;
                                c2.g.push([x + 1, y + 1]);
                                //this.draw_chess(x + 1, y + 1, 'white');
                                //console.log(x, y);

                                if (this.check_isOver(c2, c1, x, y)) {
                                        this.store.commit("updateAIResult", '输了就输了咯~💜💙💛💚');
                                        this.is_over = true;
                                        return;
                                }



                        } else {

                                let mousePos = this.getMousePos(this.ctx.canvas, e);
                                const i = Math.round(mousePos.x / this.L),
                                        j = Math.round(mousePos.y / this.L);
                                if (!this.check_valid(i, j)) return;

                                //console.log(this.store.state.user.is_me);

                                this.store.state.pk.socket.send(JSON.stringify({
                                        event: "move",
                                        x: i,
                                        y: j,
                                        color: this.store.state.user.color
                                }));




                        }

                });


        }



        render() {



                this.ctx.fillStyle = "#FFD766";
                for (let i = 0; i < this.size; i++) {
                        for (let j = 0; j < this.size; j++) {
                                this.ctx.fillRect(i * this.L, j * this.L, this.L, this.L);
                        }
                }

                this.ctx.fillStyle = "black";

                for (let i = 1; i < this.size; i++) {
                        this.ctx.beginPath();
                        this.ctx.moveTo(i * this.L, this.L);
                        this.ctx.lineTo(i * this.L, this.size * this.L - this.L);
                        this.ctx.stroke();

                        this.ctx.beginPath();
                        this.ctx.moveTo(this.L, i * this.L);
                        this.ctx.lineTo(this.size * this.L - this.L, i * this.L);
                        this.ctx.stroke();
                }
                const t = [
                        [4, 4],
                        [this.size - 4, 4],
                        [8, 8],
                        [4, 12],
                        [this.size - 4, 12],
                ];

                for (let i = 0; i < t.length; i++) {
                        const x = t[i][0],
                                y = t[i][1];
                        this.ctx.beginPath();
                        this.ctx.arc(x * this.L, y * this.L, this.L / this.size, 0, 2 * Math.PI);
                        this.ctx.stroke();
                }

                //console.log(this.stack);
                if (!this.store.state.user.is_ai_vs_human) {
                        for (const point of this.stack) {
                                this.ctx.fillStyle = point.color;
                                const x = point.i, y = point.j;
                                this.ctx.beginPath();
                                this.ctx.arc(x * this.L, y * this.L, this.L / 3, 0, 2 * Math.PI);
                                this.ctx.fill();
                                this.g[x - 1][y - 1] = point.color === 'black' ? 1 : 2;
                        }
                }



                //console.log(this.ctx);
        }
}
