<template>

    <ContentField>
        <table class="table table-striped table-hover" style="text-align: center;margin-top:5%;">
            <thead>
                <tr>
                    <th>玩家A</th>
                    <th>玩家B</th>
                    <th>对战结果</th>
                    <th>对战时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.id">
                    <td>
                        <img v-if="record.a_photo !== null" :src="record.a_photo" alt="" class="record-user-photo">
                        <img v-else src="@/assets/images/sly.png" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.a_username }}</span>
                    </td>
                    <td>
                        <img v-if="record.b_photo != null" :src="record.b_photo" alt="" class="record-user-photo">
                        <img v-else src="@/assets/images/OIP-C.jpg" alt="" class="record-user-photo">

                        &nbsp;
                        <span class="record-user-username">{{ record.b_username }}</span>
                    </td>
                    <td>{{ record.winner }}</td>
                    <td>{{ record.createtime }}</td>
                    <td>
                        <button @click="open_record_content(record.steps)" type="button"
                            class="btn btn-secondary">查看录像</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- <nav aria-label="...">
            <ul class="pagination" style="float: right;">
                <li class="page-item" @click="click_page(-2)">
                    <a class="page-link" href="#">前一页</a>
                </li>
                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number"
                    @click="click_page(page.number)">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li class="page-item" @click="click_page(-1)">
                    <a class="page-link" href="#">后一页</a>
                </li>
            </ul>
        </nav> -->
        <nav aria-label="Page navigation example" style="padding:10px; float:right;">
            <ul class="pagination">
                <li class="page-item" @click="click_page(-2)">
                    <a class="page-link" href="#" aria-label="前一页">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li @click="click_page(page.number)" :class="'page-item ' + page.active" v-for="page in pages"
                    :key="page.number">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>



                <li class="page-item " @click="click_page(-1)">
                    <a class="page-link" href="#" aria-label="后一页">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>

    </ContentField>



</template>



<script>
import ContentField from '@/components/ContentField'
import { useStore } from 'vuex';
import $ from 'jquery';
import { ref } from 'vue';
import router from '@/router/index';
export default {
    name: 'RecordIndexView',
    components: {
        ContentField,
    },
    setup() {


        const store = useStore();
        let records = ref([]);
        let current_page = 1;
        let total_records = 0;
        let pages = ref([]);




        const click_page = page => {

            if (page == -1) page = 1 + current_page;
            else if (page == -2) page = current_page - 1;

            const max_page = parseInt(Math.ceil(total_records / 6));
            if (page >= 1 && page <= max_page) {
                pull_page(page);
            }

        }


        const update_page = () => {
            const max_page = parseInt(Math.ceil(total_records / 6));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i++) {
                if (i >= 1 && i <= max_page) {
                    new_pages.push({
                        number: i,
                        active: current_page === i ? "active" : ""
                    })
                }
                //console.log(current_page + " " + i + " " + max_page + " " + total_records);
            }
            pages.value = new_pages;
            //console.log(new_pages);

        }

        const open_record_content = (steps) => {

            router.push({ name: 'check-record' });


            store.commit("updateIsRecord", true);
            store.commit("updateSteps", steps);
        }

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "https://app4450.acapp.acwing.com.cn/api/get/recordlist/",
                data: {
                    page,
                },
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {

                    records.value = resp.records;
                    total_records = resp.records_count;
                    update_page();
                    //console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page);
        return {
            records,
            click_page,
            open_record_content,
            pages,

        }

    }
}

</script>

<style scoped>
img.record-user-photo {
    width: 4vh;
    border-radius: 50%;
}

.table {
    margin: auto;

}
</style>