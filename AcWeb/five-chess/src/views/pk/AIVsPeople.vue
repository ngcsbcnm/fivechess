<template>

    <div ref="parent" class="play">

        <canvas ref="ctx">

        </canvas>
    </div>



</template>



<script>

import { ref, onMounted } from 'vue';
import { GameBoard } from '@/assets/script/GameBoard';
import { useStore } from 'vuex';

// import { GameBoard } from '@/assets/script/GameBoard'
//import { Chess } from '@/assets/script/chess';
export default {

    components: {

    },
    setup() {

        let parent = ref(null);
        let ctx = ref(null);
        const store = useStore();
        onMounted(() => {

            new GameBoard(ctx.value.getContext('2d'), parent.value, store, []);


        })

        //console.log("qowuwioetuweior");
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