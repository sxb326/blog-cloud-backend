<template>
    <el-container>
        <el-aside width="150px" class="aside-container left">
            <el-badge :value="blog.likeCount" :max="999">
                <div class="leftBtn" @click="debounceLike(blog.liked)"
                     :style="{background: blog.liked ? '#409eff' : 'white',color: blog.liked ? 'white' : 'black'}">
                    <el-icon size="20">
                        <Pointer/>
                    </el-icon>
                </div>
            </el-badge>
            <el-badge :value="blog.commentCount" :max="999">
                <div class="leftBtn" @click="openComment(blog.uid)">
                    <el-icon size="20">
                        <ChatLineRound/>
                    </el-icon>
                </div>
            </el-badge>
            <el-badge :value="blog.collectCount" :max="999">
                <div class="leftBtn" @click="debounceCollect(blog.uid)"
                     :style="{background: blog.collected ? '#409eff' : 'white',color: blog.collected ? 'white' : 'black'}">
                    <el-icon size="20">
                        <Star/>
                    </el-icon>
                </div>
            </el-badge>
        </el-aside>
        <el-main class="main-container" ref="blogRef">
            <div class="title">
                <h1 style="font-size: 2.1em">{{ blog.title }}</h1>
                <div class="blog-stats">
                    <div class="blog-stat-item">
                        <span class="author">{{ blog.authorName }}</span>
                    </div>
                    <div class="blog-stat-item">{{ blog.createTime }}</div>
                    <div class="blog-stat-item">
                        <span><el-icon class="stat-icon"><View/></el-icon></span>
                        <span>{{ blog.clickCount }}</span>
                    </div>
                    <el-button v-if="blog.isAuthor" type="primary" text @click="edit">编辑</el-button>
                </div>
            </div>
            <v-md-preview ref="previewRef" :text="blog.content"></v-md-preview>
        </el-main>
        <el-aside width="300px" class="aside-container right">
            <div class="authorDiv">
                <div class="authorInfo">
                    <el-avatar :size="45" :src="pictureUrl + author.picUid" class="centered-item avatar"/>
                    <div class="authorNickName">{{ author.nickName }}</div>
                </div>
            </div>
            <div class="directoryDiv" ref="directoryRef">
                <div v-for="anchor in titles" :key="anchor"
                     @click="directoryClick(anchor)" :id="anchor.id" class="directory-div"
                     :style="{'border-left': directoryId === anchor.id ? '2px solid #007BFF' : 'none'}">
                    <div class="directory-item"
                         :style="{ padding: `5px 0 5px ${anchor.indent * 20}px`,color: directoryId === anchor.id ? '#409eff' : 'black'}">
                        {{ anchor.title }}
                    </div>
                </div>
            </div>
        </el-aside>
        <CommentList ref="commentRef" @refresh-comment-count="refreshCommentCount"></CommentList>
        <FavoriteForm ref="favoriteRef" @refresh-collect="refreshCollect"></FavoriteForm>
    </el-container>
</template>
<script setup>
import {nextTick, onMounted, reactive, ref} from "vue";
import request from '@/utils/request.js'
import {useRoute, useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import {debounce} from "@/utils/debounce.js";
import CommentList from '@/components/comment/CommentList.vue'
import FavoriteForm from "@/components/favorite/FavoriteForm.vue";

const route = useRoute();
const router = useRouter();
const pictureUrl = ref(import.meta.env.VITE_APP_SERVICE_API + "/picture/");

let blog = reactive({})
let author = reactive({})
let loading = ref(false)
let titles = ref([])

//根据路径参数中的id 调用后端接口获取博客内容
const getBlog = () => {
    let id = route.params.id;
    blog.uid = id;
    if (id) {
        loading.value = true
        request.get('/web/preview/' + id).then(result => {
            if (result.code === '302') {
                ElMessage({
                    message: result.message,
                    type: 'warning',
                });
                router.push('/home')
                return;
            }
            Object.assign(blog, result.data);
            nextTick(() => {
                //成功获取到文章内容，调用接口查询作者信息
                request.get('/auth/getUserInfo/' + blog.authorId).then(result => {
                    Object.assign(author, result.data)
                })
                directoryInit()
                loading.value = false
            })
        })
    }
}

const previewRef = ref(null)

//初始化目录树
const directoryInit = () => {
    const anchors = previewRef.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6');
    const arr = Array.from(anchors).filter((title) => !!title.innerText.trim());
    if (!arr.length) {
        titles.value = [];
        return;
    }
    const hTags = Array.from(new Set(arr.map((title) => title.tagName))).sort();
    titles.value = arr.map((el) => ({
        id: 'directory-' + el.getAttribute('data-v-md-line'),
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
        pixel: el.getBoundingClientRect().top - 60
    }));
}

let directoryId = ref('')

//目录点击事件
const directoryClick = (anchor) => {
    const {lineIndex} = anchor;
    const heading = previewRef.value.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);
    if (heading) {
        removeScrollEventListener()
        directoryId.value = anchor.id
        previewRef.value.scrollToTarget({
            target: heading,
            scrollContainer: document.querySelector(".main-container"),
            top: 60,
        });
        setTimeout(() => {
            addScrollEventListener()
        }, 200);
    }
}

const blogRef = ref(null)
const directoryRef = ref(null)

//滚动事件监听
const scrollEventListener = () => {
    let pixel = blogRef.value.$el.scrollTop + blogRef.value.$el.offsetTop + 1
    const title = titles.value.reduce((prev, curr) => {
        if (curr.pixel <= pixel && (prev === null || pixel - curr.pixel < pixel - prev.pixel)) {
            return curr;
        }
        return prev;
    }, null);
    if (title) {
        directoryRef.value.scrollTop = (directoryRef.value.scrollHeight * title.pixel) / blogRef.value.$el.scrollHeight
        directoryId.value = title.id
    }
}

//注册滚动事件
const addScrollEventListener = () => {
    blogRef.value.$el.addEventListener('scroll', scrollEventListener);
}

//销毁滚动事件
const removeScrollEventListener = () => {
    blogRef.value.$el.removeEventListener('scroll', scrollEventListener);
}

onMounted(() => {
    getBlog()
    addScrollEventListener()
})

const like = (liked) => {
    const param = {type: 1, objUid: blog.uid, status: !liked}
    request.post('/web/like/save', param).then(result => {
        if (!result) {
            return;
        }
        ElMessage({
            message: result.message,
            type: 'success',
        });
        blog.likeCount = result.data;
        blog.liked = !liked
    })
}

const debounceLike = debounce(like, 200)

//评论
const commentRef = ref(null)

const openComment = (blogId) => {
    commentRef.value.open(blogId)
}

const refreshCommentCount = (count) => {
    blog.commentCount = count
}

const favoriteRef = ref()

//打开收藏夹
const collect = (blogId) => {
    request.get("/auth/checkLoginStatus").then(result => {
        if (!result.data) {
            ElMessage({
                message: '请先登录',
                type: 'warning',
            });
            return;
        }
        favoriteRef.value.open(blogId)
    })
}

const debounceCollect = debounce(collect, 200)

//刷新文章收藏数
const refreshCollect = () => {
    request.get('/web/blog/collectCount/' + blog.uid).then(result => {
        const collectCount = result.data;
        blog.collected = collectCount >= blog.collectCount;
        blog.collectCount = collectCount;
    })
}

//编辑文章
const edit = () => {
    router.push('/editor/' + blog.uid)
}
</script>

<style>
body {
    background-color: #f2f3f5;
}

.el-container {
    display: flex;
    align-items: stretch;
}

.main-container {
    border-radius: 5px;
    height: calc(100vh - 100px);
    flex: 1;
    padding: 0;
    background-color: #FFF;
    text-align: left;

}

.aside-container {
    height: calc(100vh - 100px);
    overflow-y: auto;
    padding-bottom: 50px;
    position: relative;
    border-radius: 5px;
}

.left {
    margin-right: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.right {
    margin-left: 20px;
}

.leftBtn {
    border: 1px solid #000000;
    padding: 15px;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    margin-bottom: 40px;
}

.leftBtn:hover {
    color: white !important;
    background: #84beff !important;
}

.authorDiv {
    width: 100%;
    background-color: #FFF;
    border-radius: 5px;
    margin-bottom: 20px;
    height: calc(100vh - 85vh);
    display: flex;
    align-items: center;
}

.authorInfo {
    align-items: center;
    display: flex;
    margin-left: 30px;
    cursor: pointer;
}

.authorNickName {
    margin-left: 10px;
}

.directoryDiv {
    width: 100%;
    background-color: #FFF;
    border-radius: 5px;
    height: calc(100vh - 45vh);
    text-align: left;
    overflow-y: auto;
    overflow-x: hidden;
    padding-top: 5px;
    font-size: 14px;
}

.directory-div:hover {
    border-left: 2px solid #007BFF !important;
}

.directory-item {
    cursor: pointer;
    margin: 0 0 0 20px;
}

.directory-item:hover {
    color: rgba(64, 158, 255, 0.93) !important;
}

.main-container:hover::-webkit-scrollbar-thumb {
    display: block;
}

.directoryDiv:hover::-webkit-scrollbar-thumb {
    display: block;
}

.title {
    padding: 0 32px;
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

.author {
    cursor: pointer;
}

.author:hover {
    color: #84beff;
}

.stat-icon {
    margin-top: 0.5rem;
    margin-right: 5px;
}

.el-drawer__header {
    margin-bottom: 0px !important;
}
</style>