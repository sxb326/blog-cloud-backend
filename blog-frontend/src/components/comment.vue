<template>
    <el-drawer v-model="drawerVisible" :title="'评论（' + count + '）'" direction="rtl" :destroy-on-close="true"
               @closed="closed">
        <div v-infinite-scroll="load" infinite-scroll-distance="10" infinite-scroll-immediate="false"
             style="overflow: auto; height: calc(100vh - 100px)">
            <div v-for="item in data" :key="item.uid">
                <el-row class="comment">
                    <el-col :span="3">
                        <el-avatar :size="40" :src="pictureUrl + item.userPicUid" class="centered-item avatar"/>
                    </el-col>
                    <el-col :span="21">
                        <el-row class="nickName">{{ item.userNickName }}</el-row>
                        <el-row class="content">{{ item.content }}</el-row>
                        <el-row class="bottom">
                            <el-col :span="7">{{ item.createTime }}</el-col>
                            <el-col :span="2" class="blog-stat-item">
                                <span><el-icon class="stat-icon"><Pointer/></el-icon></span>
                                <span>{{ item.likeCount }}</span>
                            </el-col>
                            <el-col :span="2" class="blog-stat-item">
                                <span><el-icon class="stat-icon"><ChatLineRound/></el-icon></span>
                                <span>{{ item.commentCount }}</span>
                            </el-col>
                        </el-row>
                        <el-row v-if="item.subComments.length > 0">
                            <div v-for="sub in item.subComments" :key="sub.uid">
                                <el-col :span="3">
                                    <el-avatar :size="40" :src="pictureUrl + sub.userPicUid"
                                               class="centered-item avatar"/>
                                </el-col>
                                <el-col :span="21">

                                </el-col>
                            </div>
                        </el-row>
                    </el-col>
                </el-row>
            </div>
        </div>
    </el-drawer>
</template>

<script setup>
import {ref} from "vue";
import request from '@/utils/request.js'

const drawerVisible = ref(false)
const pictureUrl = ref(import.meta.env.VITE_APP_SERVICE_API + "/picture/");

let blogId = ref('')
let count = ref(0)
let page = ref(1)
let data = ref([])

const load = () => {
    page.value++
    getData()

}

const open = (id) => {
    blogId.value = id
    getData()
    drawerVisible.value = true

}

const getData = () => {
    request.get('/web/preview/comment/' + blogId.value + '/' + page.value).then(result => {
        count.value = result.data.count
        data.value.push(...result.data.data)
    })
}

const closed = () => {
    count.value = 0
    data.value = []
    page.value = 1
}

defineExpose({
    open
})
</script>

<style scoped>
.comment {
    margin-bottom: 15px;
}

.nickName {
    cursor: pointer;
}

.nickName:hover {
    color: #84beff;
}

.content {
    word-break: break-all;
    margin-top: 5px;
}

.bottom {
    color: darkgray;
    font-size: 16px;
    margin-top: 5px;
}

.stat-icon {
    margin-top: 0.2rem;
    cursor: pointer;
}

.stat-icon:hover {
    color: #84beff;
}

.blog-stat-item {
    display: flex;
    align-items: center;
    margin-right: 20px;
    font-size: 14px;
    color: darkgray;
}

.blog-stat-item span {
    margin-right: 1px;
}
</style>