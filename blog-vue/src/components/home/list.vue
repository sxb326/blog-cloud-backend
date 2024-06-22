<template>
    <el-table :data="list" height="calc(100vh - 100px)" style="width: 100%" :show-header="false">
        <el-table-column>
            <template #default="scope">
                <div class="blog">
                    <div style="width: 80%">
                        <h3>{{ scope.row.title }}</h3>
                        <p class="blog-summary">{{ scope.row.summary }}</p>
                        <div class="blog-stats">
                            <div class="blog-stat-item">
                                <span class="author">{{ scope.row.authorName }}</span>
                            </div>
                            <div class="blog-stat-item">|</div>
                            <div class="blog-stat-item">
                                <span><el-icon class="stat-icon"><View/></el-icon></span>
                                <span>{{ scope.row.clickCount }}</span>
                            </div>
                            <div class="blog-stat-item">
                                <span><el-icon class="stat-icon"><Pointer/></el-icon></span>
                                <span>{{ scope.row.likeCount }}</span>
                            </div>
                            <div class="blog-stat-item">
                                <span><el-icon class="stat-icon"><Star/></el-icon></span>
                                <span>{{ scope.row.collectCount }}</span>
                            </div>
                        </div>
                    </div>
                    <div class="coverDiv" v-if="scope.row.picUid !== null">
                        <img :src="imgUrl + scope.row.picUid" style="width:120px;height:120px"/>
                    </div>
                </div>
            </template>
        </el-table-column>
    </el-table>
</template>

<script setup>
import {onMounted, ref} from "vue";
import request from '@/utils/request.js'

const imgUrl = import.meta.env.VITE_APP_SERVICE_API + '/picture/';

let list = ref([]);

const getList = () => {
    request.get('/web/home/list').then(result => {
        list.value = result.data;
    })
}

onMounted(getList)
</script>

<style scoped>
.blog {
    cursor: pointer;
    display: flex;
    margin: 0 0.5rem;
}

.blog-summary {
    font-size: 12px;
    color: lightgray;
}

.blog-stats {
    display: flex;
}

.blog-stat-item {
    display: flex;
    align-items: center;
    margin-right: 10px;
    font-size: 14px;
    color: darkgray;
}

.blog-stat-item span:first-child {
    margin-right: 5px;
}

.stat-icon {
    margin-top: 0.5rem;
}

.coverDiv {
    margin: 0.6rem 0.8rem 0 0.8rem;
}

.author {

}

.author:hover {
    color: #409eff;
}
</style>