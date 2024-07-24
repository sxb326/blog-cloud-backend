<template>
    <div id="app">
        <el-header class="header" style="background-color: #FFF;">
            <el-row class="header-row" justify="space-between" align="middle">
                <el-col :span="5" class="header-logo">
                    <h3>分布式博客系统</h3>
                </el-col>
                <el-col :span="9" class="header-nav">
                    <router-link to="/home" class="menuItem">首页</router-link>
                    <router-link to="/test" class="menuItem">专栏</router-link>
                </el-col>
                <el-col :span="6">
                    <el-input v-model="keyWord" style="width: 240px" placeholder="想搜索点什么呢">
                        <template #suffix>
                            <el-icon class="el-input__icon" style="cursor: pointer;" @click="doSearch">
                                <search/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-col>
                <el-col :span="4" class="header-right">
                    <el-button v-if="isUserEmpty(user)" type="primary" plain @click="openLoginForm">登录/注册
                    </el-button>
                    <div v-else class="centered-container">
                        <el-button type="primary" :icon="Edit" class="centered-item" @click="openEditor">写作
                        </el-button>
                        <el-icon size="30" class="centered-item">
                            <BellFilled/>
                        </el-icon>
                        <el-dropdown trigger="click">
                            <span class="el-dropdown-link">
                                <el-avatar :size="40" :src="pictureUrl + user.picUid" class="centered-item avatar"/>
                            </span>
                            <template #dropdown>
                                <el-card class="userCard">
                                    <el-row>
                                        <el-col :span="12">
                                            <el-avatar :size="40" :src="pictureUrl + user.picUid"
                                                       class="centered-item avatar"/>
                                        </el-col>
                                        <el-col :span="12">
                                            {{ user.nickName }}
                                        </el-col>
                                    </el-row>
                                    <el-button text @click="logout">退出登录</el-button>
                                </el-card>
                            </template>
                        </el-dropdown>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <el-main>
                <router-view class="custom-main-container"/>
            </el-main>
        </el-container>
    </div>
    <loginForm ref="loginFormRef" @refresh-page="refreshPage()"></loginForm>
</template>

<script setup>
import {ElMessage} from 'element-plus'
import {Edit, Search} from '@element-plus/icons-vue';
import {getCurrentInstance, onMounted, reactive, ref} from 'vue';
import {localStorage} from "@/utils/storage";
import request from "@/utils/request.js";

const {proxy} = getCurrentInstance();

onMounted(getAuthUser)

const pictureUrl = ref(import.meta.env.VITE_APP_SERVICE_API + "/picture/");

//获取登录用户头像uid
let user = reactive({});

//获取当前登录用户
async function getAuthUser() {
    const response = await proxy.$api.auth.getAuthUser();
    if (response.data) {
        Object.assign(user, response.data);
        localStorage.set("BLOG_USER", response.data);
    } else {
        for (const key in user) {
            if (Object.prototype.hasOwnProperty.call(user, key)) {
                delete user[key];
            }
        }
    }
}

//判断用户对象是否为空
function isUserEmpty(obj) {
    return Object.keys(obj).length === 0;
}

//登录窗口
const loginFormRef = ref()

const openLoginForm = () => {
    loginFormRef.value.open()
}

// 搜索
let keyWord = ref("");

function doSearch() {
    console.log('触发搜索,关键字:' + keyWord.value)
}

//注销
const logout = () => {
    localStorage.remove('BLOG_TOKEN')
    ElMessage({
        message: '注销成功',
        type: 'success',
    })
    refreshPage()
}

const openEditor = () => {
    request.get('/web/blog/id').then(result => {
        window.open(window.location.origin + '/#/editor/' + result.data)
    })
}

const refreshPage = () => {
    window.location.reload()
}
</script>

<style scoped>
.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 999;
    height: 60px;
    line-height: 60px;
    display: flex;
    align-items: center;
    padding: 0 15px;
    box-sizing: border-box;
}

.header-row {
    width: 100%;
    max-width: 1280px;
    margin: 0 auto;
}

.header-logo img {
    height: 20px;
}

.header-nav {
    display: flex;
    align-items: center;
}

.header-right {
    display: flex;
    align-items: center;
}

.search-input {
    width: 150px;
    margin-right: 10px;
}

#app {
    padding-top: 30px;
}

.menuItem {
    margin: 0 10px;
    cursor: pointer;
}

.menuItem:hover {
    border-bottom: 2px solid #007BFF;
}

.router-link-active {
    text-decoration: none;
    color: #646cff;
}

a {
    text-decoration: none;
    color: black;
}

.centered-container {
    display: flex;
    align-items: center;
    justify-content: center;
}

.centered-item {
    margin-right: 30px;
    cursor: pointer;
}

.centered-container :last-child {
    margin-right: 0;
}

.userCard {
    width: 200px;
}
</style>
