<template>
  

          <NavBar>

          </NavBar>
          <!-- <script src="https://fastly.jsdelivr.net/gh/stevenjoezhang/live2d-widget@latest/autoload.js"></script> -->

  <router-view/>
</template>


<script>
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import NavBar from '@/components/NavBar.vue'
export default{
    components:{
      NavBar,
    },
    

}



</script>

<style>

body {
  background-image: url("@/assets/images/bg.jpg");
  background-size: cover;
  background-color: #cccccc; /* Used if the image is unavailable */
  height: 300px; /* You must set a specified height */
  background-position: center; /* Center the image */
  background-repeat: no-repeat; /* Do not repeat the image */
}


</style>