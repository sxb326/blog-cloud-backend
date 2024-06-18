<template>
    <div id="app">
        <el-header class="header" style="background-color: #FFF;">
            <el-row class="header-row" justify="space-between" align="middle">
                <el-col :span="5" class="header-logo">
                    <h3>分布式博客系统</h3>
                </el-col>
                <el-col :span="9" class="header-nav">
                    <router-link to="/" class="menuItem">首页</router-link>
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
                    <el-button v-if="userAvatarUid == null" type="primary" plain @click="openLoginForm">登录/注册
                    </el-button>
                    <div v-else class="centered-container">
                        <el-button type="primary" :icon="Edit" class="centered-item">写作</el-button>
                        <el-icon size="30" class="centered-item">
                            <BellFilled/>
                        </el-icon>
                        <el-avatar :size="30" src="/vite.svg" class="centered-item avatar"/>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <el-main>
                <router-view/>
            </el-main>
        </el-container>
    </div>
    <loginForm ref="loginFormRef" @check-login-status="checkLoginStatus"></loginForm>
    <userPopup ref="userPopupRef" @check-login-status="checkLoginStatus"></userPopup>
</template>

<script setup>
import {Search,Edit} from '@element-plus/icons-vue';
import {onMounted, ref} from 'vue';
import {getCurrentInstance} from 'vue';
import loginForm from '@/components/login/loginForm.vue'
import userPopup from "@/components/user/userPopup.vue";

const {proxy} = getCurrentInstance();

onMounted(() => {
    checkLoginStatus();
    document.addEventListener('click', documentClick);
})

//获取登录用户头像uid
let userAvatarUid = ref("");

//获取当前登录用户
async function checkLoginStatus() {
    const response = await proxy.$api.auth.checkLoginStatus();
    if (response.data) {
        userAvatarUid.value = response.data
    } else {
        userAvatarUid.value = null;
    }
}

//登录窗口
const loginFormRef = ref()

const openLoginForm = () => {
    loginFormRef.value.open()
}

//用户信息窗口
const userPopupRef = ref();

const showUserPopup = (value) => {
    userPopupRef.value.changeStatus(value)
}

// 搜索
let keyWord = ref("");

function doSearch() {
    console.log('触发搜索,关键字:' + keyWord.value)
}

//定义点击事件 关闭用户信息窗口
const documentClick = (event) => {
    const avatarElement = document.querySelector('.centered-item.avatar');
    const popupElement = document.querySelector('.user');
    if (avatarElement || popupElement) {
        showUserPopup((avatarElement && avatarElement.contains(event.target)) || (popupElement && popupElement.contains(event.target)))
    }
};

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
</style>
