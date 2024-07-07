<template>
    <div class="blogList" v-infinite-scroll="load" infinite-scroll-distance="10" infinite-scroll-immediate="false" v-loading="loading">
        <div class="blog" v-for="item in list" :key="item.uid" @click="preview(item.uid)">
            <div style="width: 100%">
                <h3>{{ item.title }}</h3>
                <p class="blog-summary">{{ item.summary }}</p>
                <div class="blog-stats">
                    <div class="blog-stat-item">
                        <span class="author">{{ item.authorName }}</span>
                    </div>
                    <div class="blog-stat-item">|</div>
                    <div class="blog-stat-item">
                        <span><el-icon class="stat-icon"><View/></el-icon></span>
                        <span>{{ item.clickCount }}</span>
                    </div>
                    <div class="blog-stat-item">
                        <span><el-icon class="stat-icon"><Pointer/></el-icon></span>
                        <span>{{ item.likeCount }}</span>
                    </div>
                    <div class="blog-stat-item">
                        <span><el-icon class="stat-icon"><Star/></el-icon></span>
                        <span>{{ item.collectCount }}</span>
                    </div>
                </div>
            </div>
            <div class="coverDiv" v-if="item.picUid !== null">
                <img :src="imgUrl + item.picUid" style="width:120px;height:120px"/>
            </div>
        </div>
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import request from '@/utils/request.js'

const imgUrl = import.meta.env.VITE_APP_SERVICE_API + '/picture/';

let list = ref([]);
let loading = ref(false)

const getList = () => {
    loading.value = true
    request.get('/web/home/list/' + page.value).then(result => {
        list.value.push(...result.data)
        loading.value = false
    })
}

let page = ref(1)

const load = () => {
    page.value++
    getList()
}

onMounted(getList)

const preview = (id) => {
    window.open(window.location.origin + '/#/preview/' + id)
}
</script>

<style scoped>
.blogList {
    overflow: auto;
    height: calc(100vh - 100px);
    padding: 10px 5px;
}

.blog {
    cursor: pointer;
    display: flex;
    margin: 0 0.5rem;
    padding: 10px;
}

.blog:hover {
    background-color: #f2f3f5;
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