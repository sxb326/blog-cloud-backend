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
    <v-md-editor v-model="content" height="calc(100vh - 100px)"></v-md-editor>
</template>

<script setup>
import {ref} from "vue";

let title = ref("");
let content = ref("");
</script>

<style scoped>
.rowDiv {
    padding: 15px;
}
.btnDiv{
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>