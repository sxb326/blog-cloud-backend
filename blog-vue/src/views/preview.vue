<template>
    <el-container>
        <el-aside width="150px" class="aside-container" style="margin-right: 20px;">
            左侧 点赞、评论、收藏
        </el-aside>
        <el-main class="main-container">
            <v-md-preview ref="previewRef" :text="blog.content"></v-md-preview>
        </el-main>
        <el-aside width="300px" class="aside-container" style="margin-left: 20px;">
            <div class="authorDiv">作者信息</div>
            <div class="directoryDiv">
                <div v-for="anchor in titles" :key="anchor" :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
                     @click="directoryClick(anchor)">
                    <a style="cursor: pointer">{{ anchor.title }}</a>
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
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
    }));
}

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

onMounted(getBlog)
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
    overflow: auto;
}
.main-container:hover::-webkit-scrollbar-thumb {
    display: block;
}
.directoryDiv:hover::-webkit-scrollbar-thumb {
    display: block;
}

</style>