<template>
    <el-row class="rowDiv">
        <el-col :span="20">
            <el-input v-model="title" size="large" placeholder="请输入文章标题"></el-input>
        </el-col>
        <el-col :span="4" class="btnDiv">
            <el-button type="warning" plain>保存草稿</el-button>
            <el-button type="primary">发布文章</el-button>
        </el-col>
    </el-row>
    <v-md-editor v-model="content" height="calc(100vh - 100px)" :disabled-menus="[]"
                 @upload-image="uploadImage"></v-md-editor>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from 'vue-router';
import request from '@/utils/request.js'

let title = ref("");
let content = ref("");

const route = useRoute();
const router = useRouter();

//根据路径参数中的id 调用后端接口获取博客内容
const getBlog = () => {
    let id = route.params.id;
    if (id) {
        request.get('/web/blog/getBlogById/' + id).then(result => {
            if (result.code === '500') {
                router.push('/home')
            }
            title.value = result.data.title;
            content.value = result.data.content;
        })
    }
}

onMounted(getBlog)

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