<template>
    <div v-if="userShow" class="user">
        <p>布局待定</p>
        <el-button text @click="logout">退出登录</el-button>
    </div>
</template>

<script setup>
import {ref} from "vue";
import {ElMessage} from 'element-plus'
import {localStorage} from "@/utils/storage";

let userShow = ref(false);

const changeStatus = (value) => {
    userShow.value = value
}

defineExpose({
    changeStatus
})

const emit = defineEmits(['check-login-status'])

//注销
const logout = () => {
    userShow.value = false
    localStorage.remove('BLOG_TOKEN')
    emit('check-login-status')
    ElMessage({
        message: '注销成功',
        type: 'success',
    })
}
</script>

<style scoped>
.user {
    position: fixed;
    top: 60px;
    right: 10%;
    width: 240px;
    height: 280px;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    z-index: 1000;
}
</style>