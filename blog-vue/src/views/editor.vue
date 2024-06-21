<template>
    <el-row class="rowDiv" v-loading.fullscreen.lock="loading">
        <el-col :span="20">
            <el-input v-model="blog.title" size="large" placeholder="请输入文章标题"></el-input>
        </el-col>
        <el-col :span="4" class="btnDiv">
            <el-button type="warning" plain @click="saveDraft">保存草稿</el-button>
            <el-button type="primary">发布文章</el-button>
        </el-col>
    </el-row>
    <v-md-editor v-model="blog.content" height="calc(100vh - 100px)" :disabled-menus="[]"
                 @upload-image="uploadImage" :toc-nav-position-right="true" :default-show-toc="true"></v-md-editor>
</template>

<script setup>
import {onMounted, onUnmounted, reactive, ref} from "vue";
import {useRoute, useRouter} from 'vue-router';
import request from '@/utils/request.js'
import {ElMessage} from "element-plus";

let blog = reactive({});

const route = useRoute();
const router = useRouter();

//初始化时加载
let loading = ref(false)

//根据路径参数中的id 调用后端接口获取博客内容
const getBlog = () => {
    let id = route.params.id;
    blog.uid = id;
    if (id) {
        loading.value = true
        request.get('/web/blog/' + id).then(result => {
            if (result.code === '403') {
                router.push('/home')
            }
            Object.assign(blog, result.data);
            loading.value = false
        })
    }
}

//保存草稿
const saveDraft = () => {
    if(!blog.title){
        ElMessage({
            message: '标题不能为空',
            type: 'warning',
        });
        return false;
    }
    request.post('/web/blog/draft', blog).then(result => {
        blog.uid = result.data
        ElMessage({
            message: result.message,
            type: 'success',
        });
    })
}

const saveKeyListener = ref(null);

const handleSaveKeydown = (event) => {
    if (event.ctrlKey && event.key === 's') {
        saveDraft();
    }
};

//按下保存时的监听
onMounted(() => {
    getBlog();
    saveKeyListener.value = window.addEventListener('keydown', handleSaveKeydown);
})

//销毁监听器
onUnmounted(() => {
    window.removeEventListener('keydown', saveKeyListener.value);
});

//上传图片回调
const uploadImage = (event, insertImage, files) => {
    //调用图片上传服务 并回写图片相关信息
    var formData = new FormData();
    formData.append("file", files[0]);
    request.post('/picture/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(result => {
        insertImage({
            url: import.meta.env.VITE_APP_SERVICE_API + '/picture/' + result.data,
            desc: 'img',
        });
    });
}
</script>

<style scoped>
.rowDiv {
    padding: 15px;
}

.btnDiv {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>