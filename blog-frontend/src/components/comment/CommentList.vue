<template>
    <el-drawer v-model="drawerVisible" :title="'评论（' + count + '）'" direction="rtl" :destroy-on-close="true"
               @closed="closed">
        <el-row>
            <el-col :span="3">
                当前用户头像
            </el-col>
            <el-col :span="21">
                <CommentBox style="margin-bottom: 20px" :blog-uid="blogId" :parent-uid="'0'"
                            :reply-to-uid="''"></CommentBox>
            </el-col>
        </el-row>
        <div v-infinite-scroll="load" infinite-scroll-distance="10" infinite-scroll-immediate="false"
             style="overflow: auto; height: calc(100vh - 200px)">
            <div v-for="(item,index) in data" :key="item.uid">
                <el-row class="comment">
                    <el-col :span="3">
                        <el-avatar :size="35" :src="pictureUrl + item.userPicUid" class="centered-item avatar"/>
                    </el-col>
                    <el-col :span="21">
                        <el-row class="nickName">{{ item.userNickName }}</el-row>
                        <el-row class="content">{{ item.content }}</el-row>
                        <el-row class="bottom">
                            <el-col :span="7">{{ item.createTime }}</el-col>
                            <el-col :span="2" class="blog-stat-item">
                                <span :style="{color: item.liked ? '#409eff' : ''}">
                                    <el-icon class="stat-icon" @click="debounceLike(item.uid,item.liked,index)"><Pointer/></el-icon></span>
                                <span>{{ item.likeCount }}</span>
                            </el-col>
                            <el-col :span="2" class="blog-stat-item">
                                <span :style="{color: item.showBox ? '#409eff' : ''}"><el-icon class="stat-icon"
                                                                                               @click="debounceComment(item.showBox,index)"><ChatLineRound/></el-icon></span>
                                <span>{{ item.commentCount }}</span>
                            </el-col>
                        </el-row>
                        <CommentBox v-if="item.showBox" :blog-uid="blogId" :parent-uid="item.uid"
                                    :reply-to-uid="''"></CommentBox>
                        <div v-if="item.subComments.length > 0" class="level2Comment">
                            <el-row class="comment" v-for="(sub,si) in item.subComments" :key="sub.uid">
                                <el-col :span="3">
                                    <el-avatar :size="30" :src="pictureUrl + sub.userPicUid"
                                               class="centered-item avatar"/>
                                </el-col>
                                <el-col :span="21">
                                    <el-row v-if="sub.replyToUserId == null">
                                        <span class="nickName">{{ sub.userNickName }}</span>
                                        ：{{ sub.content }}
                                    </el-row>
                                    <el-row v-else>
                                        <span class="nickName">{{ sub.userNickName }}</span>
                                        &nbsp;回复&nbsp;
                                        <span class="nickName">{{ sub.replyToUserNickName }}</span>
                                        ：{{ sub.content }}
                                    </el-row>
                                    <el-row class="bottom">
                                        <el-col :span="7">{{ sub.createTime }}</el-col>
                                        <el-col :span="2" class="blog-stat-item">
                                                <span :style="{color: sub.liked ? '#409eff' : ''}">
                                                    <el-icon class="stat-icon"
                                                             @click="debounceLike(sub.uid,sub.liked,index,si)"><Pointer/></el-icon>
                                                </span>
                                            <span>{{ sub.likeCount }}</span>
                                        </el-col>
                                        <el-col :span="2" class="blog-stat-item">
                                            <span :style="{color: sub.showBox ? '#409eff' : ''}">
                                                <el-icon class="stat-icon"
                                                         @click="debounceComment(sub.showBox,index,si)"><ChatLineRound/></el-icon>
                                            </span>
                                            <span>{{ sub.commentCount }}</span>
                                        </el-col>
                                        <el-col :span="10"></el-col>
                                    </el-row>
                                    <CommentBox v-if="sub.showBox" :blog-uid="blogId" :parent-uid="item.uid"
                                                :reply-to-uid="sub.uid"></CommentBox>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </div>
    </el-drawer>
</template>

<script setup>
import {ref} from "vue";
import request from '@/utils/request.js'
import {ElMessage} from "element-plus";
import {debounce} from "@/utils/debounce.js";
import CommentBox from "@/components/comment/CommentBox.vue";

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

const like = (uid, liked, x, y) => {
    const param = {type: 2, objUid: uid, status: !liked}
    request.post('/web/like/save', param).then(result => {
        if (!result) {
            return;
        }
        ElMessage({
            message: result.message,
            type: 'success',
        });
        if (y != undefined) {
            data.value[x].subComments[y].liked = !liked
            data.value[x].subComments[y].likeCount = result.data
        } else {
            data.value[x].liked = !liked
            data.value[x].likeCount = result.data
        }
    })
}

let showIndexs = []

const comment = (showBox, x, y) => {
    showBox = showBox || false

    showIndexs.forEach(a => {
        if (a.y !== undefined) {
            data.value[a.x].subComments[a.y].showBox = false
        } else {
            data.value[a.x].showBox = false
        }
    })

    showIndexs = []

    if (!showBox) {
        showIndexs.push({x: x, y: y})
    }
    if (y != undefined) {
        data.value[x].subComments[y].showBox = !showBox
    } else {
        data.value[x].showBox = !showBox
    }
}

const debounceLike = debounce(like, 200)
const debounceComment = debounce(comment, 200)

defineExpose({
    open
})
</script>

<style scoped>
.comment {
    margin-bottom: 15px;
}

.level2Comment {
    margin-top: 15px;
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