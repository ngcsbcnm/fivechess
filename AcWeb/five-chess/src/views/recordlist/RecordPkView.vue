<template>

    <div ref="parent" class="play">

        <canvas ref="ctx">

        </canvas>
    </div>



</template>



<script>

import { onMounted, ref } from 'vue';
import { useStore } from 'vuex';

// import { GameBoard } from '@/assets/script/GameBoard'                                                                 
//import { Chess } from '@/assets/script/chess';
import { onUnmounted } from 'vue';











export default {

    components: {

    },
    setup() {

        let parent = ref(null);
        let ctx = ref(null);
        const store = useStore();
        const points = store.state.record.steps;

        const size = 16;
        const L = parseInt(Math.min(0.6 * window.innerWidth, window.innerHeight * 0.7) / size);


        const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay));

        const check_record = async () => {
            // console.log(ctx.value);
            const Ctx = ctx.value.getContext('2d');
            Ctx.canvas.width = L * size;
            Ctx.canvas.height = L * size;


            Ctx.fillStyle = "#FFD766";
            for (let i = 0; i < size; i++) {
                for (let j = 0; j < size; j++) {
                    Ctx.fillRect(i * L, j * L, L, L);
                }
            }

            Ctx.fillStyle = "black";

            for (let i = 1; i < size; i++) {
                Ctx.beginPath();
                Ctx.moveTo(i * L, L);
                Ctx.lineTo(i * L, size * L - L);
                Ctx.stroke();

                Ctx.beginPath();
                Ctx.moveTo(L, i * L);
                Ctx.lineTo(size * L - L, i * L);
                Ctx.stroke();
            }
            const t = [
                [4, 4],
                [size - 4, 4],
                [8, 8],
                [4, 12],
                [size - 4, 12],
            ];

            for (let i = 0; i < t.length; i++) {
                const x = t[i][0],
                    y = t[i][1];
                Ctx.beginPath();
                Ctx.arc(x * L, y * L, L / size, 0, 2 * Math.PI);
                Ctx.stroke();
            }

            //console.log(stack);

            for (const point of points) {


                Ctx.fillStyle = point.color;
                const x = point.x, y = point.y;
                Ctx.beginPath();
                Ctx.arc(x * L, y * L, L / 3, 0, 2 * Math.PI);
                Ctx.fill();

                await sleep(1000);






            }

        }


        onMounted(() => {
            if (ctx.value !== null) {
                check_record();

            }
        })

        //console.log("qowuwioetuweior");
        onUnmounted(() => {

            store.commit("updateIsRecord", false);
            store.commit("updateSteps", []);
        })

        return {
            parent,
            ctx
        }



    }
}

</script>

<style scoped>
.play {

    width: 60vw;
    height: 70vh;
    margin-top: 40px;
    margin-left: 500px;

}
</style>