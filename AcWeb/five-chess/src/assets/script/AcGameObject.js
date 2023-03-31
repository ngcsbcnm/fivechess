const AC_GAME_OBJECT = [];


export class AcGameObject{

        constructor(){

            AC_GAME_OBJECT.push(this);
            
            this.has_call_start = false;

        }

        start(){


        }

        update(){


        }

        destory(){

            for(let i in AC_GAME_OBJECT){
                const obj = AC_GAME_OBJECT[i];
                if(obj== this){
                    AC_GAME_OBJECT.splice(i);
                }
            }

        }


}

const steps = () => {

        for(const obj of AC_GAME_OBJECT){
            if(!obj.has_call_start){
                obj.has_call_start = true;
                obj.start();
            }else{
                obj.update();
            }
        }
        requestAnimationFrame(steps);


}

requestAnimationFrame(steps);
