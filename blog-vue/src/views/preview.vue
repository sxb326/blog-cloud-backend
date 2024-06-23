<template>
    <el-container>
        <el-aside width="150px" class="aside-container" style="margin-right: 20px;">
            左侧 点赞、评论、收藏
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
                </div>
            </div>
            <v-md-preview ref="previewRef" :text="blog.content"></v-md-preview>
        </el-main>
        <el-aside width="300px" class="aside-container" style="margin-left: 20px;">
            <div class="authorDiv">作者信息</div>
            <div class="directoryDiv">
                <div v-for="anchor in titles" :key="anchor"
                     :style="{ padding: `5px 0 5px ${anchor.indent * 20}px`,color: directoryId === anchor.id ? '#409eff' : 'black' }"
                     @click="directoryClick(anchor)" class="directory-item" :id="anchor.id">
                    {{ anchor.title }}
                </div>
            </div>
        </el-aside>
    </el-container>
</template>
<script setup>
import {nextTick, onMounted, reactive, ref} from "vue";
import request from '@/utils/request.js'
import {useRoute, useRouter} from 'vue-router';
import {ElMessage} from "element-plus";

const route = useRoute();
const router = useRouter();

let blog = reactive({})
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
                directoryInit()
                loading.value = false
            })
        })
    }
}

const previewRef = ref(null)

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

const directoryClick = (anchor) => {
    const {lineIndex} = anchor;
    const heading = previewRef.value.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);
    if (heading) {
        previewRef.value.scrollToTarget({
            target: heading,
            scrollContainer: document.querySelector(".main-container"),
            top: 60,
        });
    }
}

const blogRef = ref(null)

const handleScroll = () => {
    //滚动到的像素位置
    let pixel = blogRef.value.$el.scrollTop + blogRef.value.$el.offsetTop + 1
    const closestTitle = titles.value.reduce((prev, curr) => {
        if (curr.pixel <= pixel && (prev === null || pixel - curr.pixel < pixel - prev.pixel)) {
            return curr;
        }
        return prev;
    }, null);
    directoryId.value = closestTitle ? closestTitle.id : null;
}

onMounted(() => {
    getBlog();
    blogRef.value.$el.addEventListener('scroll', handleScroll);
})
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

.authorDiv {
    width: 100%;
    background-color: #FFF;
    border-radius: 5px;
    margin-bottom: 20px;
    height: calc(100vh - 80vh);
}

.directoryDiv {
    width: 100%;
    background-color: #FFF;
    border-radius: 5px;
    height: calc(100vh - 50vh);
    text-align: left;
    overflow-y: auto;
    overflow-x: hidden;
    padding-top: 5px;
    font-size: 14px;
}

.directory-item {
    cursor: pointer;
    margin: 0 0 0 20px;
}

.directory-item:hover {
    color: #409eff;
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
    color: #409eff;
}

.stat-icon {
    margin-top: 0.5rem;
    margin-right: 5px;
}
</style>