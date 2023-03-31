<template>
    <ContentField>
        <table class="table table-striped table-hover align-middle" style="text-align: center;">
            <thead>
                <tr>
                    <th>段位</th>
                    <th>玩家</th>
                    <th>昵称</th>
                    <th>天梯分</th>
                </tr>
            </thead>
            <tbody style="vertical-align: bottom;">
                <tr v-for="user in users" :key="user.id">
                    <td>



                        <img v-if='user.rating <= 1500' src="@/assets/images/黑铁.png" alt="" class="record-user-photo">
                        <img v-else-if='user.rating <= 1600' src="@/assets/images/白银.png" alt=""
                            class="record-user-photo">
                        <img v-else-if='user.rating <= 1700' src="@/assets/images/黄金.png" alt=""
                            class="record-user-photo">
                        <img v-else-if='user.rating <= 1800' src="@/assets/images/铂金.png" alt=""
                            class="record-user-photo">
                        <img v-else-if='user.rating <= 1900' src="@/assets/images/钻石.png" alt=""
                            class="record-user-photo">
                        <img v-else-if='user.rating <= 2000' src="@/assets/images/超凡大师.png" alt=""
                            class="record-user-photo">
                        <img v-else src="@/assets/images/最强王者.png" alt="" class="record-user-photo">



                        &nbsp;

                        <span v-if="user.rating >= 2000" class="record-user-rank" style="color:burlywood">{{
                            user.rank
                        }}</span>
                        <span v-else class="record-user-rank">{{ user.rank }}</span>


                    </td>
                    <td>



                        <img v-if="user.photo !== null && user.photo !== ''" :src="user.photo" alt=""
                            class="record-user-photo">
                        <img v-else src="@/assets/images/OIP-C.jpg" alt="" class="record-user-photo">
                        &nbsp;
                        <span v-if="user.rating >= 2000" class="record-user-rank" style="color:burlywood">{{
                            user.username
                        }}</span>
                        <span v-else class="record-user-rank">{{ user.username }}</span>



                    </td>
                    <td>{{ user.nick }}</td>
                    <td>{{ user.rating }}</td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="...">
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
        </nav>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery';

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let users = ref([]);
        let current_page = 1;
        let total_users = 0;
        let pages = ref([]);

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_users / 6));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const udpate_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users / 6));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "https://app4450.acapp.acwing.com.cn/api/ranklist/getlist/",
                data: {
                    page,
                },
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    users.value = resp.users;
                    //console.log(resp);
                    total_users = resp.users_count;
                    udpate_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page);

        return {
            users,
            pages,
            click_page
        }
    }
}
</script>

<style scoped>
.record-user-rank {
    font-weight: 800;
}

img.record-user-photo {
    width: 4vh;
    border-radius: 50%;



}
</style>
