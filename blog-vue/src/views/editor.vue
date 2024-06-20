<template>
    <el-row>
        <el-col :span="22">
            <el-input v-model="title" size="large" placeholder="请输入文章标题"></el-input>
        </el-col>
        <el-col :span="2">
            <el-button type="primary" style="margin: 3px 25px">发布</el-button>
        </el-col>
    </el-row>
    <v-md-editor v-model="content" height="calc(100vh - 50px)"></v-md-editor>
</template>

<script setup>
import {ref} from "vue";

let title = ref("");
let content = ref("");
</script>

<style scoped>

</style>